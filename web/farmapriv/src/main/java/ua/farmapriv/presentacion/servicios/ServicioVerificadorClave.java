package ua.farmapriv.presentacion.servicios;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

import ua.farmapriv.presentacion.utilerias.ConstantesParametros;
import ua.farmapriv.presentacion.utilerias.UtilCryptography;
import ua.farmapriv.servicios.modelos.parametros.ParametroSistema;
import ua.farmapriv.servicios.servicios.parametros.IServicioParametro;
import ua.farmapriv.servicios.utilerias.NombresParametros;

public class ServicioVerificadorClave extends PasswordMatcher {

	private IServicioParametro servicioParametro;
	
	public ServicioVerificadorClave() {
		super();
		try {
			Context context = new InitialContext();
			servicioParametro = (IServicioParametro)context.lookup(NombresParametros.JNDI_SERVICIO_PARAMETRO);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        Object claveIngresada = getSubmittedPassword(token);
        Object claveUsuario = getStoredPassword(info);
        String claveEncriptada = null;
		try {
			ParametroSistema param = servicioParametro.obtParam(ConstantesParametros.PASSPHRASE_ENCRIPTACION);
			claveEncriptada = UtilCryptography.encriptar(param.getValor(),new String((char[])claveIngresada));
		} catch (Exception e) {
			e.printStackTrace();
		}

        String claveComparar = (String)claveUsuario;
        return claveEncriptada.equals(claveComparar);
    }
}
