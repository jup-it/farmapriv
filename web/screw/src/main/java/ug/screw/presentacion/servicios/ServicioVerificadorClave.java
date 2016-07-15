package ug.screw.presentacion.servicios;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.servicios.parametros.IServicioParametro;
import ug.screw.servicios.utilerias.NombresParametros;
import ug.screw.presentacion.utilerias.ConstantesParametros;
import ug.screw.presentacion.utilerias.UtilCryptography;

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
