package ug.screw.presentacion.beans.seguridad;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import ug.screw.presentacion.beans.parametros.ParametroBean;
import ug.screw.presentacion.servicios.ServicioNotificacion;
import ug.screw.presentacion.utilerias.ConstantesParametros;
import ug.screw.presentacion.utilerias.JSFUtils;
import ug.screw.presentacion.utilerias.UtilCryptography;
import ug.screw.presentacion.utilerias.excepciones.ExcepcionEncriptacion;
import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.modelos.seguridad.Usuario;
import ug.screw.servicios.servicios.seguridad.IServicioSeguridad;
import ug.screw.servicios.utilerias.NombresParametros;

@ManagedBean(name="loginMB")
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	@EJB
	private ServicioNotificacion servicioNotificacion;
	
	@ManagedProperty(value="#{parametrosMB}")
	private ParametroBean parametrosBean;
	
	private String usuario;
	private String clave;
	private String claveConfirmacion;
	private String codigoAutorizacion;
	private String claveSecreta;
	private Boolean reset;
	private String passPhrase;
	private String regExpClave;
	private String descExpClave;
	
	@PostConstruct
	public void init(){
		reset = true;
		usuario = "";
		clave = "";
		codigoAutorizacion = "";
		claveConfirmacion = "";
		claveSecreta = "";
		Subject entidadRequest = SecurityUtils.getSubject();
		if(entidadRequest.isAuthenticated()){
			Usuario usuarioAutenticado = (Usuario)entidadRequest.getSession().getAttribute("userCredentials");
			usuario = usuarioAutenticado.getUsuario();
		}
		ParametroSistema paramPP = parametrosBean.obtenerParametro(ConstantesParametros.PASSPHRASE_ENCRIPTACION);
		passPhrase = paramPP.getValor();
		paramPP = parametrosBean.obtenerParametro(ConstantesParametros.EXPRESION_VALIDA_CLAVE);
		regExpClave = paramPP.getValor();
		descExpClave = paramPP.getDescripcion();
		
		
//		ResteasyClient client = new ResteasyClientBuilder().build();
//        ResteasyWebTarget target = client.target("http://api.elsevier.com/content/article/pii:S1697791213000587?APIKey=c8ffa2cb10db08ff313c2e2ad6a81c0f");
//        String response = target.request().get(String.class);
//        System.out.println("*****************************");
//        System.out.println(response);
//        client.close();
	}

	public String getRegExpClave() {
		return regExpClave;
	}
	public void setRegExpClave(String regExpClave) {
		this.regExpClave = regExpClave;
	}
	public String getDescExpClave() {
		return descExpClave;
	}
	public void setDescExpClave(String descExpClave) {
		this.descExpClave = descExpClave;
	}
	public String getPassPhrase() {
		return passPhrase;
	}
	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}
	public String getClaveConfirmacion() {
		return claveConfirmacion;
	}
	public void setClaveConfirmacion(String claveConfirmacion) {
		this.claveConfirmacion = claveConfirmacion;
	}
	public ParametroBean getParametrosBean() {
		return parametrosBean;
	}
	public void setParametrosBean(ParametroBean parametrosBean) {
		this.parametrosBean = parametrosBean;
	}
	public Boolean getReset() {
		return reset;
	}
	public void setReset(Boolean reset) {
		this.reset = reset;
	}
	public String getClaveSecreta() {
		return claveSecreta;
	}
	public void setClaveSecreta(String claveSecreta) {
		this.claveSecreta = claveSecreta;
	}
	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}
	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String iniciarSesion(){
		Subject usuarioActual = SecurityUtils.getSubject();
		if(!usuarioActual.isAuthenticated()){
			usuarioActual.logout();
		}
		if("".equals(usuario) || "".equals(clave)){
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
            		JSFUtils.getMensajeLocalizado("LoginBean.incorrect_credentials"), "W");
			return "";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(usuario, clave);
        token.setRememberMe(false);
        try {
        	usuarioActual.login(token);
        }catch (Exception e) {
        	if(!(e instanceof AuthenticationException))
        		e.printStackTrace();
            JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
            		JSFUtils.getMensajeLocalizado("LoginBean.incorrect_credentials"), "W");
            return "";
		}
        Usuario usuarioLogin = servicioSeguridad.obtUsu(usuario);
        usuarioActual.getSession().setAttribute("userCredentials", usuarioLogin);
        if(usuarioLogin.getCambiarClave())
        	return ConstantesParametros.RUTA_CAMBIO_CLAVE;
        else
        	return ConstantesParametros.RUTA_PRINCIPAL;
	}
	
	public String irACambioClave(){
		return ConstantesParametros.RUTA_CAMBIO_CLAVE;
	}
	
	public String cerrarSesion(){
		Subject usuarioActual = SecurityUtils.getSubject();
		usuarioActual.logout();
		usuario = codigoAutorizacion = "";
		return ConstantesParametros.RUTA_LOGIN;
	}
	
	public String resetClave(){
		try {
			if(claveSecreta.equals("")){
				claveSecreta = generaCodigoAutorizacion();
				Usuario usrRst = servicioSeguridad.obtUsu(usuario);
				reset = false;
				servicioNotificacion.notificacionBienvenida("arosero@corlasosa.com", usrRst.getCorreo(), null, "Clave Autorizacion", claveSecreta);
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
			    		JSFUtils.getMensajeLocalizado("LoginBean.email_sended"), "I");
			}else{
				if(!codigoAutorizacion.equals(claveSecreta)){
					JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
			        		JSFUtils.getMensajeLocalizado("LoginBean.enter_autorization_code"), "W");
				}else{
					Usuario usuRst = servicioSeguridad.obtUsu(usuario);
					if(usuRst != null){
						ParametroSistema param = parametrosBean.obtenerParametro(ConstantesParametros.CLAVE_DEFECTO);
						usuRst.setClave(UtilCryptography.encriptar(passPhrase, param.getValor()));
						usuRst.setCambiarClave(true);
						usuRst.setFechaActualizacion(new Date());
						usuRst.setUsuarioModificacion(usuRst);
						servicioSeguridad.actUsuario(usuRst);
						JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
			            		JSFUtils.getMensajeLocalizado("LoginBean.password_reset_ok"), "I");
						cerrarSesion();
//						return ConstantesParametros.RUTA_CAMBIO_CLAVE;
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
            		JSFUtils.getMensajeLocalizado("transaction_error"), "W");
		}
		
		return "";
	}
	
	public String generaCodigoAutorizacion() throws ExcepcionEncriptacion{
		return UtilCryptography.encriptar(passPhrase, usuario + Calendar.getInstance().getTimeInMillis());
	}
	
	public String cambiarClave(){
		try {
			if("".equals(clave) || "".equals(claveConfirmacion)){
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
			    		JSFUtils.getMensajeLocalizado("LoginBean.enter_all_fields"), "W");
				System.err.println("cae en bklanco");
				return "";
			}
			String mensaje = validarClaves();
			if(mensaje == null){
				Usuario usuLogin = servicioSeguridad.obtUsu(usuario);
				usuLogin.setFechaActualizacion(new Date());
				usuLogin.setUsuarioModificacion(usuLogin);
				usuLogin.setCambiarClave(false);
				usuLogin.setClave(UtilCryptography.encriptar(passPhrase, claveConfirmacion));
				servicioSeguridad.actUsuario(usuLogin);
				JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("info_message_title"), 
			    		JSFUtils.getMensajeLocalizado("transaction_complete"), "W");
				return ConstantesParametros.RUTA_PRINCIPAL;
			}
			System.err.println("valida is blank");
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado(mensaje), "W");
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtils.mostrarMensajeCentrado(JSFUtils.getMensajeLocalizado("warning_message_title"), 
					JSFUtils.getMensajeLocalizado("transaction_error"), "W");
		}
		System.err.println("by error");
		return "";
	}
	private String validarClaves(){
		Usuario usuLogin = servicioSeguridad.obtUsu(usuario);
		if(usuLogin == null)
			return "LoginBean.user_not_exists";
		if(!clave.equals(claveConfirmacion))
			return "LoginBean.password_not_match";
		Pattern pattern = Pattern.compile(regExpClave); 
		Matcher matcher = pattern.matcher(clave);
		if(!matcher.find())
			return "LoginBean.password_regex_nomatch";
		return null;
	}
}
