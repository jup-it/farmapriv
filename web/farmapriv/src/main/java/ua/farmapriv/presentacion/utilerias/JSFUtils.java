package ua.farmapriv.presentacion.utilerias;

import java.security.Principal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

public class JSFUtils {
	
	private static ResourceBundle bundle;
	
	static{
		 bundle = ResourceBundle.getBundle(ConstantesParametros.MENSAJES_CANONICAL_NAME);
	}
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}   

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getFacesContext().getExternalContext()
				.getRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) getFacesContext().getExternalContext()
				.getResponse();
	}
	
	public static HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}
	
	public static String getRealPath(String pRutaRelativa){
		return getHttpServletRequest().getSession().getServletContext().getRealPath(pRutaRelativa);
	}
	

	public static String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}

	public static Application getApplication() {
		return getFacesContext().getApplication();
	}
	
//	public static <T> T findManagedBean(String managedBeanName, Class<T> beanClass){
//		return beanClass.cast(getApplication().evaluateExpressionGet(getFacesContext(), "#{"+managedBeanName+"}", beanClass));
//	}

	public static FacesMessage crearMensaje(String pSumario, String pDetalle,
			FacesMessage.Severity pSeverity) {
		FacesMessage l_fm = new FacesMessage();
		l_fm.setSummary(pSumario);
		l_fm.setDetail(pDetalle);
		l_fm.setSeverity(pSeverity);
		return l_fm;
	}

	public static FacesMessage crearMensajeError(String pMensajeError, String pDetalle) {
		return crearMensaje(pMensajeError, pDetalle,
				FacesMessage.SEVERITY_ERROR);
	}

	public static FacesMessage crearMensajeError(String pMensajeError) {
		return crearMensajeError(pMensajeError, "");
	}

	public static FacesMessage crearMensajeInformacion(String pMensajeInformacion,
			String pDetalle) {
		return crearMensaje(pMensajeInformacion, pDetalle,
				FacesMessage.SEVERITY_INFO);
	}

	public static FacesMessage crearMensajeInformacion(String pMensajeError) {
		return crearMensajeInformacion(pMensajeError, "");
	}

	public static void aniadirMensaje(FacesMessage pMensaje, String pIdComponenteJSF) {
		getFacesContext().addMessage(pIdComponenteJSF, pMensaje);
	}

	public static void aniadirMensaje(FacesMessage pMensaje) {
		aniadirMensaje(pMensaje, null);
	}

	public static void aniadirMensajeInformacion(String pMensajeInformacion,
			String pDetalleMensaje) {
		aniadirMensaje(crearMensajeInformacion(pMensajeInformacion,
				pDetalleMensaje));
	}

	public static void aniadirMensajeInformacion(String pMensajeInformacion) {
		aniadirMensajeInformacion(pMensajeInformacion, "");
	}

	public static void aniadirMensajeInformacionComponente(String pComponentId,
			String pMensajeInformacion, String pDetalleMensaje) {
		aniadirMensaje(
				crearMensajeInformacion(pMensajeInformacion, pDetalleMensaje),
				pComponentId);
	}

	public static void aniadirMensajeInformacionComponente(String pComponentId,
			String pMensajeInformacion) {
		aniadirMensajeInformacionComponente(pComponentId, pMensajeInformacion,
				"");
	}

	public static void aniadirMensajeError(String pSumario, String pDetalle) {
		aniadirMensaje(crearMensajeError(pSumario, pDetalle));
	}

	public static void aniadirMensajeError(String pSumario) {
		aniadirMensajeError(pSumario, "");
	}

	public static void aniadirMensajeErrorComponente(String pClientId,
			String pSumario, String pDetalleMensaje) {
		aniadirMensaje(crearMensajeError(pSumario, pDetalleMensaje), pClientId);
	}

	public static void aniadirMensajeErrorComponente(String pClientId, String pSumario) {
		aniadirMensaje(crearMensajeError(pSumario), pClientId);
	}

	public static void aniadirMensajeError(Throwable pException) {
		pException.printStackTrace();
		aniadirMensajeError(pException.getMessage());
	}
	
	public static void mostrarMensajeCentrado(String pSumario,String pDetalle, String level){
		Severity severity;
		if (level.equalsIgnoreCase("E")) {
			severity = FacesMessage.SEVERITY_ERROR;
		}else if(level.equalsIgnoreCase("W"))
			severity = FacesMessage.SEVERITY_WARN;
		else if(level.equalsIgnoreCase("F"))
			severity = FacesMessage.SEVERITY_FATAL;
		else
			severity = FacesMessage.SEVERITY_INFO;
			
		FacesMessage message = new FacesMessage(severity, pSumario, pDetalle);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void setRequestAttribute(String pAttribute, Object pValor) {
		getFacesContext().getExternalContext().getRequestMap()
				.put(pAttribute, pValor);
	}

	public static void clearRequestAttribute(String pAttribute) {
		getFacesContext().getExternalContext().getRequestMap()
				.remove(pAttribute);
	}
	
	public static void removeRequestValue(Object pValue) {
		removeValueFromMap(pValue,getFacesContext().getExternalContext().getRequestMap());
	}

	private static void removeValueFromMap(Object pValue,Map<?,?> pMap){
		Object lClaveEncontrada=null;
		if(pMap.containsValue(pValue)){
			for(Object lClave:pMap.keySet()){
				if(pMap.get(lClave).equals(pValue)){
					lClaveEncontrada=lClave;
					break;
				}
			}
			pMap.remove(lClaveEncontrada);
		}
	}
	
	public static void setSessionAttribute(String pAttribute, Object pValor) {
		getFacesContext().getExternalContext().getSessionMap()
				.put(pAttribute, pValor);
	}

	public static void clearSessionAttribute(String pAttribute) {
		getFacesContext().getExternalContext().getSessionMap()
				.remove(pAttribute);
	}
	
	public static void removeSessionValue(Object pValue) {
		removeValueFromMap(pValue,getFacesContext().getExternalContext().getSessionMap());
	}

	public static Object getRequestAttribute(String pAttribute) {
		return getFacesContext().getExternalContext().getRequestMap()
				.get(pAttribute);
	}

	public static Object getSessionAttribute(String pAttribute) {
		return getFacesContext().getExternalContext().getSessionMap()
				.get(pAttribute);
	}

	public static String getRequestParameter(String pParameter) {
		return getFacesContext().getExternalContext().getRequestParameterMap()
				.get(pParameter);
	}

	public static String[] getRequestParameters(String pParameter) {
		return getFacesContext().getExternalContext()
				.getRequestParameterValuesMap().get(pParameter);
	}

	public static boolean isAutenticado() {
		return ((HttpServletRequest) getFacesContext().getExternalContext()
				.getRequest()).getUserPrincipal() != null;
	}
	
	public static boolean isUserInRole(String pRol) {
		return ((HttpServletRequest) getFacesContext().getExternalContext()
				.getRequest()).isUserInRole(pRol);
	}

	public static Principal getUsuarioAutenticado() {
		return ((HttpServletRequest) getFacesContext().getExternalContext()
				.getRequest()).getUserPrincipal();
	}

	public static String getNombreUsuarioAutenticado() {
		Principal lPrincipal = getUsuarioAutenticado();
		String lNombreUsuario = null;
		if (lPrincipal != null)
			lNombreUsuario = lPrincipal.getName();
		return lNombreUsuario;
	}

	public static Locale getDefaultLocale() {
		return Locale.getDefault();
	}

	public static Locale getClientLocale() {
		return getFacesContext().getViewRoot().getLocale();
	}
	
	public static String getCurrentTimeZone() {
		return Calendar.getInstance().getTimeZone().getID();
	}

	public static boolean isFacesContexConError() {
		Severity lSeverity = getFacesContext().getMaximumSeverity();
		return FacesMessage.SEVERITY_ERROR.equals(lSeverity)
				|| FacesMessage.SEVERITY_FATAL.equals(lSeverity);
	}
	
	public static void removerFacesMessage(FacesMessage.Severity pSeverity){
		for(Iterator<FacesMessage>i=getFacesContext().getMessages();i.hasNext();){
			FacesMessage lFacesMessage=i.next();
			if(lFacesMessage.getSeverity().equals(pSeverity))
				i.remove();
		}
	}
	
	public static void removerFacesMessageInformacion(){
		removerFacesMessage(FacesMessage.SEVERITY_INFO);
	}
	public static void setNombreArchivoDescargar(String pNombreArchivo){
		getHttpServletResponse().setHeader("Content-Disposition", "inline; filename=\""
				+ pNombreArchivo + "\"");
	}
	public static void setContentType(String pTipoContenido){
		getHttpServletResponse().setContentType(pTipoContenido);
	}
	public static void setContentTypeArchivoTexto(){
		getHttpServletResponse().setContentType("application/txt");
	}
	
	public static String getMensajeLocalizado(String etiquetaMensaje){
	    if(bundle != null)
	    	return bundle.getString(etiquetaMensaje);
	    return "Undefined";
	}
}
