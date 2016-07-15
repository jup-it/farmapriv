package ug.screw.presentacion.beans.parametros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import ug.screw.presentacion.utilerias.JSFUtils;
import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.modelos.parametros.TipoParametroSistema;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.servicios.parametros.IServicioParametro;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;
import ug.screw.servicios.utilerias.NombresParametros;

@ManagedBean(name="parametrosMB")
@ApplicationScoped
public class ParametroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB(lookup=NombresParametros.JNDI_SERVICIO_PARAMETRO)
	private IServicioParametro servicioParametro;
	
	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	private ParametroSistema parametroIngreso;
	private TipoParametroSistema tipoParametroIngreso;
	private Long idTipoParametro;
	private List<TipoParametroSistema> listaTipoParametro;
	private List<ParametroSistema> listaParametro;
	private List<SelectItem> listaFiltroTiposParametros;
	private Boolean mostrarBotonGuardar;
	private Boolean mostrarBotonActualizar;
	
	@PostConstruct
	public void init(){
		idTipoParametro = new Long(0);
		parametroIngreso = new ParametroSistema();
		tipoParametroIngreso = new TipoParametroSistema();
		listaFiltroTiposParametros = new ArrayList<SelectItem>();
		listaParametro = servicioParametro.obtParam();
		listaTipoParametro = servicioParametro.obtTipParam();
		mostrarBotonActualizar = false;
		mostrarBotonGuardar = true;
	}
	
	public ParametroSistema getParametroIngreso() {
		return parametroIngreso;
	}
	public void setParametroIngreso(ParametroSistema parametroIngreso) {
		this.parametroIngreso = parametroIngreso;
	}
	
	public TipoParametroSistema getTipoParametroIngreso() {
		return tipoParametroIngreso;
	}
	public void setTipoParametroIngreso(TipoParametroSistema tipoParametroIngreso) {
		this.tipoParametroIngreso = tipoParametroIngreso;
	}

	public Long getIdTipoParametro() {
		return idTipoParametro;
	}
	public void setIdTipoParametro(Long idTipoParametro) {
		this.idTipoParametro = idTipoParametro;
	}

	public List<TipoParametroSistema> getListaTipoParametro() {
		return listaTipoParametro;
	}
	public void setListaTipoParametro(List<TipoParametroSistema> listaTipoParametro) {
		this.listaTipoParametro = listaTipoParametro;
	}

	public List<ParametroSistema> getListaParametro() {
		return listaParametro;
	}
	public void setListaParametro(List<ParametroSistema> listaParametro) {
		this.listaParametro = listaParametro;
	}

	public Boolean getMostrarBotonGuardar() {
		return mostrarBotonGuardar;
	}
	public void setMostrarBotonGuardar(Boolean mostrarBotonGuardar) {
		this.mostrarBotonGuardar = mostrarBotonGuardar;
	}

	public Boolean getMostrarBotonActualizar() {
		return mostrarBotonActualizar;
	}
	public void setMostrarBotonActualizar(Boolean mostrarBotonActualizar) {
		this.mostrarBotonActualizar = mostrarBotonActualizar;
	}
	
	public List<SelectItem> getListaFiltroTiposParametros() {
		return listaFiltroTiposParametros;
	}
	public void setListaFiltroTiposParametros(
			List<SelectItem> listaFiltroTiposParametros) {
		this.listaFiltroTiposParametros = listaFiltroTiposParametros;
	}
	
	public void ingresoParametroSistema(){
		if(idTipoParametro.equals("")||idTipoParametro==0){
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("select_param_type"), "W");
		}
		if(parametroIngreso.getDescripcion().equals("")||parametroIngreso.getValor().equals("")){
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("enter_all_fields"), "W");
		}
		else{
			Subject subject = SecurityUtils.getSubject();
			Usuario usuarioModificacion = servicioSeguridad.obtUsu(subject.getPrincipal().toString());
			parametroIngreso.setEstado("A");
			parametroIngreso.setFechaCreacion(new Date());
			tipoParametroIngreso.setId(idTipoParametro);
			parametroIngreso.setTipoParametro(tipoParametroIngreso);
			parametroIngreso.setUsuarioModificacion(usuarioModificacion);
			servicioParametro.crearParam(parametroIngreso);
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
					JSFUtils.getMensajeLocalizado("saved_correctly"), "I");
			init();
		}		
	}
	
	public void actualizarParametroSistema() {  
		if(parametroIngreso==null){
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("sel_rec_to_upd"), "W");
		}else{
			if(idTipoParametro.equals("")||idTipoParametro==0){
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
						JSFUtils.getMensajeLocalizado("select_param_type"), "W");
			}else if(parametroIngreso.getDescripcion().equals("")||parametroIngreso.getValor().equals("")){
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
						JSFUtils.getMensajeLocalizado("enter_all_fields"), "W");
			}else{
				Subject subject = SecurityUtils.getSubject();
				Usuario usuarioModificacion = servicioSeguridad.obtUsu(subject.getPrincipal().toString());
				tipoParametroIngreso.setId(idTipoParametro);
				parametroIngreso.setFechaActualizacion(new Date());
				parametroIngreso.setUsuarioModificacion(usuarioModificacion);
				servicioParametro.actParam(parametroIngreso);
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
						JSFUtils.getMensajeLocalizado("updated_correctly"), "I");
				init();
			}
		}		
    }
	
	public void elegirParametro(ActionEvent evento){
		parametroIngreso = (ParametroSistema) evento.getComponent().getAttributes().get("ParametroActualizar");
		tipoParametroIngreso=parametroIngreso.getTipoParametro();
		idTipoParametro=tipoParametroIngreso.getId();
		mostrarBotonActualizar = true;
		mostrarBotonGuardar = false;
	}
	
	public ParametroSistema obtenerParametro(String nombre){
		for(ParametroSistema param : listaParametro){
			if(param.getNombre().equalsIgnoreCase(nombre))
				return param;
		}
		return null;
	}
	
}
