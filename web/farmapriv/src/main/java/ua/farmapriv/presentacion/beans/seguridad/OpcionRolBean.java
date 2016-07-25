package ua.farmapriv.presentacion.beans.seguridad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import ua.farmapriv.presentacion.beans.parametros.ParametroBean;
import ua.farmapriv.presentacion.utilerias.ConstantesParametros;
import ua.farmapriv.presentacion.utilerias.JSFUtils;
import ua.farmapriv.servicios.modelos.seguridad.Opcion;
import ua.farmapriv.servicios.modelos.seguridad.OpcionRol;
import ua.farmapriv.servicios.modelos.seguridad.Rol;
import ua.farmapriv.servicios.modelos.seguridad.Usuario;
import ua.farmapriv.servicios.servicios.seguridad.IServicioSeguridad;
import ua.farmapriv.servicios.utilerias.NombresParametros;

@ManagedBean(name="opcionRolMB")
@ApplicationScoped
public class OpcionRolBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	@ManagedProperty(value="#{parametrosMB}")
	private ParametroBean parametrosBean;
	
	private List<Rol> listaRolesActivos;
	private List<Rol> listaRolesTodos;
	private List<Opcion> listaOpciones;
	private Rol nuevoRol;
	private Usuario usuarioModificacion;
	
	private Boolean[][] opcionesAsignadas;
	private boolean presentarBotonActvivarRol;
	
	@PostConstruct
	private void inicializacion(){		
		listaRolesActivos = servicioSeguridad.obtRolXEst(ConstantesParametros.ESTADO_ACTIVO);		
		listaOpciones = servicioSeguridad.obtOpcTerm();
		opcionesAsignadas();
		
		listaRolesTodos = servicioSeguridad.obtRol();
		nuevoRol = new Rol();
		
		presentarBotonActvivarRol=false;
		Subject subject = SecurityUtils.getSubject();
		usuarioModificacion = servicioSeguridad.obtUsu(subject.getPrincipal().toString());
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
	public List<Rol> getListaRolesActivos() {
		return listaRolesActivos;
	}
	public void setListaRolesActivos(List<Rol> listaRolesActivos) {
		this.listaRolesActivos = listaRolesActivos;
	}
	public List<Opcion> getListaOpciones() {
		return listaOpciones;
	}
	public void setListaOpciones(List<Opcion> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}
	public Rol getNuevoRol() {
		return nuevoRol;
	}
	public void setNuevoRol(Rol nuevoRol) {
		this.nuevoRol = nuevoRol;
	}
	public Boolean[][] getOpcionesAsignadas() {
		return opcionesAsignadas;
	}
	public void setOpcionesAsignadas(Boolean[][] opcionesAsignadas) {
		this.opcionesAsignadas = opcionesAsignadas;
	}
	public boolean isPresentarBotonActvivarRol() {
		return presentarBotonActvivarRol;
	}
	public void setPresentarBotonActvivarRol(boolean presentarBotonActvivarRol) {
		this.presentarBotonActvivarRol = presentarBotonActvivarRol;
	}
	public List<Rol> getListaRolesTodos() {
		return listaRolesTodos;
	}
	public void setListaRolesTodos(List<Rol> listaRolesTodos) {
		this.listaRolesTodos = listaRolesTodos;
	}
	
	public void opcionesAsignadas(){
		int cont=0, iRol=0, iOpc=0;
		opcionesAsignadas = new Boolean[listaRolesActivos.size()][listaOpciones.size()];
		for (Rol i : listaRolesActivos) {			
			List <OpcionRol> opcionesTemporalesAsignadas = servicioSeguridad.obtOpcXRol(i);		
			for (Opcion opcTerm : listaOpciones) {
				cont=1;
				for (OpcionRol opcAs : opcionesTemporalesAsignadas) {
					if(opcTerm.getIdOpcion()==opcAs.getOpcion().getIdOpcion()){
						opcionesAsignadas[iOpc][iRol]= true;
						iRol++;
						break;
					}else if(opcionesTemporalesAsignadas.size()==cont){	
						opcionesAsignadas[iOpc][iRol]= false;
						iRol++;
					}
					cont++;
				}							
			}
			iRol=0;
			iOpc++;			
		}
	}
	
	public void ingresoActualizarOpcionesRoles(Rol rol, Opcion opc){		
		try {
			OpcionRol opcRol = servicioSeguridad.obtOpcRol(rol, opc);
			if(opcRol != null){
				if(NombresParametros.ESTADO_ACTIVO.equals(opcRol.getEstado())){
					opcRol.setEstado(NombresParametros.ESTADO_INACTIVO);
				}else{
					opcRol.setEstado(NombresParametros.ESTADO_ACTIVO);				
				}
				opcRol.setFechaActualizacion(new Date());
				opcRol.setUsuarioModificacion(usuarioModificacion);
				servicioSeguridad.actOpcRol(opcRol);
			}else{
				OpcionRol opcRolIng = new OpcionRol(); 
				opcRolIng.setRol(rol);
				opcRolIng.setOpcion(opc);
				opcRolIng.setEstado(NombresParametros.ESTADO_ACTIVO);
				opcRolIng.setFechaCreacion(new Date());
				opcRolIng.setUsuarioModificacion(usuarioModificacion);
				servicioSeguridad.crearOpcRol(opcRolIng);			
				//INGRESO DE OPCION PADRE 
				Opcion IngOpcPad = opc.getOpcionPadre();
				OpcionRol opcRolPadreI = servicioSeguridad.obtOpcRol(rol,IngOpcPad);
				if(opcRolPadreI == null){
					OpcionRol opcRolIngP = new OpcionRol(); 
					opcRolIngP.setRol(rol);
					opcRolIngP.setOpcion(IngOpcPad);
					opcRolIngP.setEstado(NombresParametros.ESTADO_ACTIVO);
					opcRolIngP.setFechaCreacion(new Date());
					opcRolIngP.setUsuarioModificacion(usuarioModificacion);
					servicioSeguridad.crearOpcRol(opcRolIngP);
				}
			}		
			Opcion OpcPadre = opc.getOpcionPadre();
			OpcionRol opcRolPadre = servicioSeguridad.obtOpcRol(rol,OpcPadre);		
			if(opcRolPadre != null){
				if(servicioSeguridad.obtCanXPad(rol, OpcPadre) < 1 ){
					opcRolPadre.setEstado(NombresParametros.ESTADO_INACTIVO);
				}else{
					opcRolPadre.setEstado(NombresParametros.ESTADO_ACTIVO);
				}
				opcRolPadre.setFechaActualizacion(new Date());
				opcRolPadre.setUsuarioModificacion(usuarioModificacion);
				servicioSeguridad.actOpcRol(opcRolPadre);
			}
			
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_complete"), "I");
		} catch (Throwable e) {
			e.printStackTrace();
			inicializacion();
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_error"), "W");
		}
	}
	
	public void guardarRolNuevo()
	{
		nuevoRol.setRol(nuevoRol.getRol().toUpperCase());
		nuevoRol.setEstado("A");
		nuevoRol.setFechaCreacion(new Date());
		nuevoRol.setUsuarioModificacion(usuarioModificacion);
		servicioSeguridad.crearRol(nuevoRol);
		inicializacion();
	}

	public void activarInactivarRol(Rol rol)
	{
		rol.setEstado(rol.getEstado().equals("A")?"I":"A");
		rol.setFechaActualizacion(new Date());
		rol.setUsuarioModificacion(usuarioModificacion);
		rol = servicioSeguridad.actRol(rol);
		inicializacion();
	}

}