package ug.screw.servicios.servicios.impl.seguridad;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ug.screw.servicios.eao.seguridad.OpcionEAO;
import ug.screw.servicios.eao.seguridad.OpcionRolEAO;
import ug.screw.servicios.eao.seguridad.RolEAO;
import ug.screw.servicios.eao.seguridad.UsuarioEAO;
import ug.screw.servicios.eao.seguridad.UsuarioRolEAO;
import ug.screw.servicios.modelos.seguridad.Opcion;
import ug.screw.servicios.modelos.seguridad.OpcionRol;
import ug.screw.servicios.modelos.seguridad.Rol;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.modelos.seguridad.UsuarioRol;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;

@Stateless
public class ServicioSeguridad implements IServicioSeguridad{

	@EJB
	private UsuarioEAO usuarioEAO;
	
	@EJB
	private RolEAO rolEAO;
	
	@EJB
	private OpcionEAO opcionEAO;
	
	@EJB
	private UsuarioRolEAO usuarioRolEAO;
	
	@EJB
	private OpcionRolEAO opcionRolEAO;

	
	@Override
	public Opcion obtOpc(Opcion opcion){
		return opcionEAO.buscarPorId(opcion.getClass(), opcion.getIdOpcion());
	}
	
	@Override
	public List<Opcion> obtOpcPad(){
		return opcionEAO.obtOpcPad();
	}
	
	@Override
	public List<Opcion> obtOpcHij(Opcion opcionPadre){
		return opcionEAO.obtOpcHij(opcionPadre);
	}
	
	@Override
	public List<Opcion> obtOpcXEst(String estado) {
		return opcionEAO.obtOpcXEst(estado);
	}
	
	@Override
	public List<Opcion> obtOpcTerm(){
		return opcionEAO.obtOpcTerm();
	}

	@Override
	public Long obtCanXPad(Rol rol, Opcion opcionPadre) {
		return opcionRolEAO.obtCanXPad(rol, opcionPadre);
	}
	
	@Override
	public void crearOpcRol(OpcionRol entidad) {
		opcionRolEAO.crear(entidad);		
	}

	@Override
	public OpcionRol actOpcRol(OpcionRol entidad) {
		return opcionRolEAO.actualizar(entidad);
	}

	@Override
	public OpcionRol refOpcRol(OpcionRol entidad) {
		return opcionRolEAO.refrescar(entidad, entidad.getId());
	}
	
	@Override
	public OpcionRol obtOpcRol(Rol rol, Opcion opcion){
		return opcionRolEAO.obtOpcRol(rol, opcion);
	}
	
	@Override
	public List<OpcionRol> obtOpcXRol(Rol rol) {
		return opcionRolEAO.obtOpcXRol(rol);
	}

	@Override
	public List<OpcionRol> obtOpcRolTod() {
		return opcionRolEAO.obtOpcRolTod();
	}

	@Override
	public void crearRol(Rol entidad) {
		rolEAO.crear(entidad);
	}

	@Override
	public Rol actRol(Rol entidad) {
		return rolEAO.actualizar(entidad);
	}

	@Override
	public Rol refRol(Rol entidad) {
		return rolEAO.refrescar(entidad, entidad.getId());
	}
	
	@Override
	public Rol obtRolXNom(String nombre){
		return rolEAO.obtRol(nombre);
	}
	
	@Override
	public List<Rol> obtRol() {
		return rolEAO.obtRol();
	}

	@Override
	public List<Rol> obtRolXEst(String estado) {
		return rolEAO.obtRolXEst(estado);
	}
	
	@Override
	public void crearUsuario(Usuario entidad) {
		usuarioEAO.crear(entidad);
	}
	
	@Override
	public Usuario refUsuario(Usuario entidad) {
		return usuarioEAO.refrescar(entidad, entidad.getId());
	}

	@Override
	public Usuario actUsuario(Usuario entidad) {
		return usuarioEAO.actualizar(entidad);
	}
	
	@Override
	public Usuario obtUsuXCred(String usuario, String clave) {
		return usuarioEAO.obtUsuXCred(usuario, clave);
	}

	@Override
	public Usuario obtUsu(String usuario) {
		return usuarioEAO.obtUsu(usuario);
	}

	@Override
	public List<Usuario> obtUsuAct() {
		return usuarioEAO.obtUsuAct();
	}
	
	@Override
	public List<Usuario> obtUsuTod(){
		return usuarioEAO.obtUsuTod();
	}
	
	@Override
	public List<Usuario> obtUsuXPat(String patron){
		return usuarioEAO.obtUsuXPat(patron);
	}

	@Override
	public void crearUsuRol(UsuarioRol entidad) {
		usuarioRolEAO.crear(entidad);
	}

	@Override
	public UsuarioRol actUsuRol(UsuarioRol entidad) {
		return usuarioRolEAO.actualizar(entidad);
	}

	@Override
	public UsuarioRol refUsuRol(UsuarioRol entidad) {
		return usuarioRolEAO.refrescar(entidad, entidad.getId());
	}
	
	@Override
	public void inacUsuRol(Usuario usuario, Usuario usuarioModificacion){
		usuarioRolEAO.inacUsuRol(usuario,usuarioModificacion);
	}
	
	@Override
	public List<UsuarioRol> obtRolAsig(Usuario usuario) {
		return usuarioRolEAO.obtRolAsig(usuario);
	}

}
