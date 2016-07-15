package ug.screw.presentacion.servicios;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import ug.screw.servicios.modelos.seguridad.OpcionRol;
import ug.screw.servicios.modelos.seguridad.Rol;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.modelos.seguridad.UsuarioRol;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;
import ug.screw.servicios.utilerias.NombresParametros;

public class ServicioRealm extends JdbcRealm {

	private IServicioSeguridad servicioSeguridad;
	
	public ServicioRealm() {
		super();
		try {
			Context context = new InitialContext();
			servicioSeguridad = (IServicioSeguridad)context.lookup(NombresParametros.JNDI_SERVICIO_SEGURIDAD);
			permissionsLookupEnabled = true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String usuario = upToken.getUsername();

		if (usuario == null) {
			throw new AccountException(
					"El usuario ingresado es nulo");
		}

		SimpleAuthenticationInfo info = null;
		try {
			Usuario entidadUsuario = servicioSeguridad.obtUsu(usuario);
			String password = entidadUsuario.getClave();
			
			info = new SimpleAuthenticationInfo(usuario, password.toCharArray(), getName());

		} catch (Throwable e) {
			// Rethrow any SQL errors as an authentication exception
			throw new AuthenticationException("Ocurrio un problema al autenticar al usuario ["+ usuario +"]", e);
		}

		return info;

	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		//null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("El usuario no esta autenticado.");
        }

        String usuario = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = null;
        Set<String> permissions = null;
        try {

            // Retrieve roles and permissions from database
            roleNames = getRoleNamesForUser(null, usuario);
            if (permissionsLookupEnabled) {
                permissions = getPermissions(null, usuario, roleNames);
            }

        } catch (SQLException e) {
            final String message = "There was a SQL error while authorizing user [" + usuario + "]";

            // Rethrow any SQL errors as an authorization exception
            throw new AuthorizationException(message, e);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
	}
	
	@Override
	protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        Set<String> roleNames = new LinkedHashSet<String>();
        try {
        	Usuario entidadUsuario = servicioSeguridad.obtUsu(username);
        	List<UsuarioRol> listaRolesAsignados = servicioSeguridad.obtRolAsig(entidadUsuario);
            // Loop over results and add each returned role to a set
        	System.err.println("roles para usuario :" + username);
            for(UsuarioRol usuRol : listaRolesAsignados){
                String rol = usuRol.getRol().getRol();
                // Add the role to the list of names if it isn't null
                roleNames.add(rol);
                System.err.println(rol);
            }
        }catch(Throwable t){
        	throw new SQLException(t);
        }
        return roleNames;
    }
	
	@Override
	protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        Set<String> listaPermisos = new LinkedHashSet<String>();
        for (String rol : roleNames) {
            try {
            	Rol entidadRol = new Rol();
            	entidadRol.setRol(rol);
            	List<OpcionRol> listaOpcionesAsignadas = servicioSeguridad.obtOpcXRol(entidadRol);

            	System.err.println("permisos para usuario :" + username);
                // Loop over results and add each returned role to a set
                for(OpcionRol opRol : listaOpcionesAsignadas) {
                    String permiso = opRol.getOpcion().getNombre() + ":" + "*";
                    // Add the permission to the set of permissions
                    listaPermisos.add(permiso);
                    System.err.println(permiso);
                }
            } catch(Throwable t){
            	throw new SQLException(t);
            }

        }

        return listaPermisos;
    }
}
