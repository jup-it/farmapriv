package ua.farmapriv.presentacion.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ua.farmapriv.presentacion.utilerias.ConstantesParametros;
import ua.farmapriv.presentacion.utilerias.JSFUtils;
import ua.farmapriv.servicios.modelos.seguridad.Opcion;
import ua.farmapriv.servicios.servicios.seguridad.IServicioSeguridad;
import ua.farmapriv.servicios.utilerias.NombresParametros;

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
