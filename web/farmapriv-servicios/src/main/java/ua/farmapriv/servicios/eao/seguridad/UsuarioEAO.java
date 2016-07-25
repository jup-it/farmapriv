package ua.farmapriv.servicios.eao.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ua.farmapriv.servicios.eao.EAOBasico;
import ua.farmapriv.servicios.modelos.seguridad.Usuario;
import ua.farmapriv.servicios.utilerias.NombresParametros;

@Stateless
@SuppressWarnings("unchecked")
public class UsuarioEAO extends EAOBasico<Usuario> {

	private static final String CONSULTA_TODOS = "Select u from Usuario u order by u.usuario";
	private static final String CONSULTA_x_ESTADO = "Select p from Usuario p where p.estado = :estado";
	private static final String CONSULTA_x_USUARIO = "Select p from Usuario p where p.usuario = :usuario and p.estado = :estado";
	private static final String CONSULTA_x_LOGIN = "Select p from Usuario p where p.usuario = :usuario and p.clave = :clave and p.estado = :estado";
	private static final String CONSULTA_x_PATRON = "Select u from Usuario u where u.usuario like :usuario";

	public Usuario obtUsuXCred(String usuario, String clave){
		Query q = adminEntidad.createQuery(CONSULTA_x_LOGIN);
		q.setParameter("usuario", usuario);
		q.setParameter("clave", clave);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		
		try {
			return (Usuario)q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Usuario obtUsu(String usuario){
		Query q = adminEntidad.createQuery(CONSULTA_x_USUARIO);
		q.setParameter("usuario", usuario);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return (Usuario)q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Usuario> obtUsuAct(){
		List<Usuario> resultado = new ArrayList<Usuario>();
		Query q = adminEntidad.createQuery(CONSULTA_x_ESTADO);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<Usuario> obtUsuTod(){
		List<Usuario> resultado = new ArrayList<Usuario>();
		Query q = adminEntidad.createQuery(CONSULTA_TODOS);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<Usuario> obtUsuXPat(String patron){
		List<Usuario> resultado = new ArrayList<Usuario>();
		Query q = adminEntidad.createQuery(CONSULTA_x_PATRON);
		q.setParameter("usuario", patron);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
}