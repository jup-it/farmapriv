package ug.screw.presentacion.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ug.screw.servicios.modelos.seguridad.Opcion;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;
import ug.screw.servicios.utilerias.NombresParametros;
import ug.screw.presentacion.utilerias.ConstantesParametros;
import ug.screw.presentacion.utilerias.JSFUtils;

@ManagedBean(eager=true,name="configuracionesGeneralesMB")
@ApplicationScoped
public class ConfiguracionesGeneralesBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	private List<Opcion> listaOpcionesAplicacion;
	
	@PostConstruct
	public void init(){
		listaOpcionesAplicacion = servicioSeguridad.obtOpcXEst(NombresParametros.ESTADO_ACTIVO);
	}
	
	public List<Opcion> getListaOpcionesAplicacion() {
		return listaOpcionesAplicacion;
	}
	public void setListaOpcionesAplicacion(List<Opcion> listaOpcionesAplicacion) {
		this.listaOpcionesAplicacion = listaOpcionesAplicacion;
	}
	
	public String getRutaAplicacion(){
		return JSFUtils.getContextPath() + ConstantesParametros.RUTA_LOGIN;
	}

}
