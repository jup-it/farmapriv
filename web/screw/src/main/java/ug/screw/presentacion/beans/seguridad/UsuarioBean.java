package ug.screw.presentacion.beans.seguridad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.SecurityUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import ug.screw.presentacion.beans.parametros.ParametroBean;
import ug.screw.presentacion.utilerias.ConstantesParametros;
import ug.screw.presentacion.utilerias.JSFUtils;
import ug.screw.presentacion.utilerias.UtilCryptography;
import ug.screw.presentacion.utilerias.excepciones.ExcepcionEncriptacion;
import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.modelos.seguridad.Rol;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.modelos.seguridad.UsuarioRol;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;
import ug.screw.servicios.utilerias.NombresParametros;

@ManagedBean(name="usuarioMB")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	@ManagedProperty(value="#{parametrosMB}")
	private ParametroBean parametrosBean;
	
	private String lsCriterioBusquedaUsuario;
	private String timeZone;
	private Usuario usuario;
	private Usuario usuarioAsignar;
	private List<Usuario> listaTodosUsuarios;
	private List<Rol> rolesAsignar;
	private List<Rol> rolesAsignados;
	private List<UsuarioRol> usuarioRolesAsignados;
	private DualListModel<Rol> rolesPresentar;
	private Usuario usuarioModificacion;
		
	@PostConstruct
	public void init(){
		nuevoUsuario();
		timeZone = JSFUtils.getCurrentTimeZone();
		listaTodosUsuarios = servicioSeguridad.obtUsuTod();
		rolesPresentar = new DualListModel<Rol>(new ArrayList<Rol>(),new ArrayList<Rol>());
		lsCriterioBusquedaUsuario = "";
		usuarioModificacion = servicioSeguridad.obtUsu(SecurityUtils.getSubject().getPrincipal().toString());
	}
	
	public void nuevoUsuario(){
		usuario = new Usuario();
	}
	public void listaTablaEnter()
	{
		if (lsCriterioBusquedaUsuario.equals("")) {
			listaTodosUsuarios = servicioSeguridad.obtUsuTod();
		}else {
			listaTodosUsuarios = servicioSeguridad.obtUsuXPat(lsCriterioBusquedaUsuario);
		}
	}
	
	public void elegirDatoUsuario(ActionEvent evento) throws Throwable {
		usuario = (Usuario) evento.getComponent().getAttributes().get("datosUsuario");
		usuario = servicioSeguridad.refUsuario(usuario);
	}
	
	public void asignarRolUsuario(ActionEvent evento) {
		rolesAsignados = new ArrayList<Rol>();
		usuarioAsignar = (Usuario) evento.getComponent().getAttributes().get("datosUsuario");
		try {
			usuarioRolesAsignados = servicioSeguridad.obtRolAsig(usuarioAsignar);
			for (UsuarioRol userRoles : usuarioRolesAsignados) {
				rolesAsignados.add(userRoles.getRol());
			}
			rolesAsignar = obtenerRolesPorAsignar(usuarioRolesAsignados);
			rolesPresentar = new DualListModel<Rol>(rolesAsignar,rolesAsignados);
		} catch (Throwable e) {
			e.printStackTrace();
			rolesAsignados = new ArrayList<Rol>();
			rolesAsignar = new ArrayList<Rol>();
		}
	}
	
	public void cambiarEstadoUsuarios() {
		String mensaje = null;
		String tipoMensaje = null;
		if (usuario.getEstado().equals("A")) {
			usuario.setEstado("I");
			tipoMensaje = "warning_message_title";
			mensaje = "UsuarioBean.user_inactivated";
		} else {
			usuario.setEstado("A");
			tipoMensaje = "info_message_title";
			mensaje = "UsuarioBean.user_activated";
		}
		usuario.setUsuarioModificacion(usuarioModificacion);
		usuario.setFechaActualizacion(new Date());
		servicioSeguridad.actUsuario(usuario);
		listaTodosUsuarios = servicioSeguridad.obtUsuTod();
		JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado(tipoMensaje),JSFUtils.getMensajeLocalizado(mensaje), "I");
	}
	
	public void editarUsuario() {
		if (usuario.getCorreo() == null || usuario.getCorreo().equals("")) {
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("UsuarioBean.invalid_mail"), "W");
		} else {
			try {
				usuario.setUsuarioModificacion(usuarioModificacion);
				usuario.setFechaActualizacion(new Date());
				servicioSeguridad.actUsuario(usuario);
				listaTodosUsuarios = servicioSeguridad.obtUsuTod();
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
						JSFUtils.getMensajeLocalizado("UsuarioBean.updated_correctly"), "I");
			}catch (Exception s) {
				s.printStackTrace();				
			}
		}
	}
	
	public void ingresarUsuario() {		
		try {
			if (servicioSeguridad.obtUsu(usuario.getUsuario()) != null) {
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"),
						JSFUtils.getMensajeLocalizado("user_exists"),"W");
			} else if (usuario.getCorreo() == null || usuario.getCorreo().equals("")) {
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
						JSFUtils.getMensajeLocalizado("invalid_mail"),"W");
			} else 	{
				String passPhrase = parametrosBean.obtenerParametro(ConstantesParametros.PASSPHRASE_ENCRIPTACION).getValor();
				String claveDefecto = parametrosBean.obtenerParametro(ConstantesParametros.CLAVE_DEFECTO).getValor();
				usuario.setClave(UtilCryptography.encriptar(passPhrase,claveDefecto));
				usuario.setEstado(NombresParametros.ESTADO_ACTIVO);
				usuario.setCambiarClave(true);
				usuario.setFechaCreacion(new Date());
				servicioSeguridad.crearUsuario(usuario);
				listaTodosUsuarios = servicioSeguridad.obtUsuTod();		
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
						JSFUtils.getMensajeLocalizado("saved_correctly"),"I");
			}
		} catch (Throwable e) {
			RequestContext.getCurrentInstance().addCallbackParam("error", true);
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_error"), "W");
			e.printStackTrace();
		}
	}
	
	public void actualizarRoles() {
		List<Rol> lista = rolesPresentar.getTarget();
		try {
			asignarRoles(lista, usuarioAsignar);
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
					JSFUtils.getMensajeLocalizado("updated_correctly"), "I");
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_error"), "W");
		}
	}

	public void asignarRoles(List<Rol> listaRoles, Usuario usuario)
			throws Throwable {
		inactivarRoles(usuario);
		for(Rol rol : listaRoles){
			UsuarioRol usuRol = new UsuarioRol();
			usuRol.setRol(rol);
			usuRol.setEstado(NombresParametros.ESTADO_ACTIVO);
			usuRol.setFechaCreacion(new Date());
			usuRol.setUsuario(usuario);
			usuRol.setUsuarioModificacion(usuarioModificacion);
			servicioSeguridad.crearUsuRol(usuRol);
		}
	}

	public void inactivarRoles(Usuario usuario) throws Throwable {
		servicioSeguridad.inacUsuRol(usuario, usuarioModificacion);
	}
	
	public List<Rol> obtenerRolesPorAsignar(List<UsuarioRol> listaUsuariosRoles){
		List<Rol> listaRoles = servicioSeguridad.obtRolXEst(NombresParametros.ESTADO_ACTIVO);
		List<Rol> listaRolesNoAsignados = new ArrayList<Rol>();
		for(Rol rol : listaRoles){
			boolean encontrado = false;
			for(UsuarioRol usuRol : listaUsuariosRoles){
				if(rol.getRol().equals(usuRol.getRol().getRol()))
						encontrado = true;	 
			}
			if(!encontrado)
				listaRolesNoAsignados.add(rol);
		}
		return listaRolesNoAsignados;
	}
	
	public void resetClave(){
		try {
			ParametroSistema paramPassPhrase = parametrosBean.obtenerParametro(ConstantesParametros.PASSPHRASE_ENCRIPTACION);
			ParametroSistema paramClaveDefecto = parametrosBean.obtenerParametro(ConstantesParametros.CLAVE_DEFECTO);
			String claveDefecto = paramClaveDefecto.getValor();
			String passPhrase = paramPassPhrase.getValor();
			usuario.setCambiarClave(true);
			usuario.setClave(UtilCryptography.encriptar(passPhrase, claveDefecto));
			usuario.setFechaActualizacion(new Date());
			usuario.setUsuarioModificacion(usuarioModificacion);
			servicioSeguridad.actUsuario(usuario);
			listaTodosUsuarios = servicioSeguridad.obtUsuTod();
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
					JSFUtils.getMensajeLocalizado("updated_correctly"), "I");
		} catch (ExcepcionEncriptacion e) {
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_error"), "W");
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public ParametroBean getParametrosBean() {
		return parametrosBean;
	}

	public void setParametrosBean(ParametroBean parametrosBean) {
		this.parametrosBean = parametrosBean;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLsCriterioBusquedaUsuario() {
		return lsCriterioBusquedaUsuario;
	}

	public void setLsCriterioBusquedaUsuario(String lsCriterioBusquedaUsuario) {
		this.lsCriterioBusquedaUsuario = lsCriterioBusquedaUsuario;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public List<Usuario> getListaTodosUsuarios() {
		return listaTodosUsuarios;
	}

	public void setListaTodosUsuarios(List<Usuario> listaTodosUsuarios) {
		this.listaTodosUsuarios = listaTodosUsuarios;
	}

	public Usuario getUsuarioAsignar() {
		return usuarioAsignar;
	}

	public void setUsuarioAsignar(Usuario usuarioAsignar) {
		this.usuarioAsignar = usuarioAsignar;
	}

	public List<Rol> getRolesAsignar() {
		return rolesAsignar;
	}

	public void setRolesAsignar(List<Rol> rolesAsignar) {
		this.rolesAsignar = rolesAsignar;
	}

	public List<Rol> getRolesAsignados() {
		return rolesAsignados;
	}

	public void setRolesAsignados(List<Rol> rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}

	public List<UsuarioRol> getUsuarioRolesAsignados() {
		return usuarioRolesAsignados;
	}

	public void setUsuarioRolesAsignados(List<UsuarioRol> usuarioRolesAsignados) {
		this.usuarioRolesAsignados = usuarioRolesAsignados;
	}

	public DualListModel<Rol> getRolesPresentar() {
		return rolesPresentar;
	}

	public void setRolesPresentar(DualListModel<Rol> rolesPresentar) {
		this.rolesPresentar = rolesPresentar;
	}
}
