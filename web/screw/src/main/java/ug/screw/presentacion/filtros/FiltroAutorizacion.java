package ug.screw.presentacion.filtros;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import ug.screw.servicios.modelos.seguridad.Opcion;
import ug.screw.presentacion.beans.ConfiguracionesGeneralesBean;

public class FiltroAutorizacion extends PermissionsAuthorizationFilter {
	
	public boolean isAccessAllowed(ServletRequest request,
									ServletResponse response, 
									Object mappedValue) throws IOException {
		if ((request instanceof HttpServletRequest)) {
			HttpServletRequest lrequest = (HttpServletRequest) request;
			Subject subject = getSubject(request, response);
			String lsUrl = lrequest.getRequestURI();
			if (lsUrl.endsWith(".xml") ||
					lsUrl.endsWith(".xhtml") ||
					lsUrl.endsWith(".jsf") ||
					lsUrl.endsWith(".ini")){
				if (!subject.isAuthenticated()){
					return false;
				}
			}
			lsUrl = lsUrl.replace(lrequest.getContextPath(), "");
			lsUrl = lsUrl.split(";")[0];
			return subject.isPermitted(obtenerPermisoRequerido(lrequest,lsUrl));
		}
		
		return false;
	}
	
	private String obtenerPermisoRequerido(HttpServletRequest lrequest, String url){
		ConfiguracionesGeneralesBean configuracionesGeneralesBean 
			= (ConfiguracionesGeneralesBean)lrequest.getServletContext().getAttribute("configuracionesGeneralesMB");
		List<Opcion> listaOpcionesApp = configuracionesGeneralesBean.getListaOpcionesAplicacion();
		for (Opcion opcion : listaOpcionesApp) {
			if(opcion.getOpcionPadre() != null && opcion.getRutaAplicacion().equalsIgnoreCase(url))
				return opcion.getNombre()+":*";
		}
		return "Desconocido:*";
	}
}
