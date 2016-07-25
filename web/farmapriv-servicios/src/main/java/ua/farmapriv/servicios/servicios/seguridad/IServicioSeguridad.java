package ua.farmapriv.servicios.servicios.seguridad;

import java.util.List;

import javax.ejb.Remote;

import ua.farmapriv.servicios.modelos.seguridad.Opcion;
import ua.farmapriv.servicios.modelos.seguridad.OpcionRol;
import ua.farmapriv.servicios.modelos.seguridad.Rol;
import ua.farmapriv.servicios.modelos.seguridad.Usuario;
import ua.farmapriv.servicios.modelos.seguridad.UsuarioRol;

@Remote
public interface IServicioSeguridad {

	public Opcion obtOpc(Opcion opcion);
	public List<Opcion> obtOpcPad();
	public List<Opcion> obtOpcHij(Opcion opcionPadre);
	public List<Opcion> obtOpcXEst(String estado);
	public List<Opcion> obtOpcTerm();
	public Long obtCanXPad(Rol rol, Opcion opcionPadre);
	public void crearOpcRol(OpcionRol entidad);
	public OpcionRol actOpcRol(OpcionRol entidad);
	public OpcionRol refOpcRol(OpcionRol entidad);
	public OpcionRol obtOpcRol(Rol rol, Opcion opcion);
	public List<OpcionRol> obtOpcXRol(Rol rol);
	public List<OpcionRol> obtOpcRolTod();
	public void crearRol(Rol entidad);
	public Rol actRol(Rol entidad);
	public Rol refRol(Rol entidad);
	public Rol obtRolXNom(String nombre);
	public List<Rol> obtRol();
	public List<Rol> obtRolXEst(String estado);
	public void crearUsuario(Usuario entidad);
	public Usuario refUsuario(Usuario entidad);
	public Usuario actUsuario(Usuario entidad);
	public Usuario obtUsuXCred(String usuario, String clave);
	public Usuario obtUsu(String usuario);
	public List<Usuario> obtUsuAct();
	public List<Usuario> obtUsuTod();
	public List<Usuario> obtUsuXPat(String patron);
	public void crearUsuRol(UsuarioRol entidad);
	public UsuarioRol actUsuRol(UsuarioRol entidad);
	public UsuarioRol refUsuRol(UsuarioRol entidad);
	public void inacUsuRol(Usuario usuario, Usuario usuarioModificacion);
	public List<UsuarioRol> obtRolAsig(Usuario usuario);
}
