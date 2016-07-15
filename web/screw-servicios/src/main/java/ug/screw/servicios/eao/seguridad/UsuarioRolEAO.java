package ug.screw.servicios.eao.seguridad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ug.screw.servicios.eao.EAOBasico;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.modelos.seguridad.UsuarioRol;
import ug.screw.servicios.utilerias.NombresParametros;

@Stateless
@SuppressWarnings("unchecked")
public class UsuarioRolEAO extends EAOBasico<UsuarioRol> {
	
	private static final String CONSULTA_ROLES_ASIGNADOS = "Select r from UsuarioRol r Where r.usuario.id = :idUsuario and r.estado = :estado";
	//private static final String INACTIVA_ROLES_x_USUARIO = "Update UsuarioRol u set u.estado = :estado,u.usuario.id=:idUsuarioModificacion where u.usuario.id = :idUsuario";

	public List<UsuarioRol> obtRolAsig(Usuario usuario){
		List<UsuarioRol> resultado = new ArrayList<UsuarioRol>();
		Query q = adminEntidad.createQuery(CONSULTA_ROLES_ASIGNADOS);
		q.setParameter("idUsuario", usuario.getId());
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public void inacUsuRol(Usuario usuario,Usuario usuarioModificacion){
//		Query q = adminEntidad.createQuery(INACTIVA_ROLES_x_USUARIO);
//		q.setParameter("estado", NombresParametros.ESTADO_INACTIVO);
//		q.setParameter("idUsuarioModificacion", usuarioModificacion.getId());
//		q.setParameter("idUsuario", usuario.getId());
//		q.executeUpdate();
		List<UsuarioRol> rolesActivos = obtRolAsig(usuario);
		for (UsuarioRol usuarioRol : rolesActivos) {
			usuarioRol.setEstado(NombresParametros.ESTADO_INACTIVO);
			usuarioRol.setUsuarioModificacion(usuarioModificacion);
			usuarioRol.setFechaActualizacion(new Date());
			adminEntidad.merge(usuarioRol);
		}
	}
	
}
