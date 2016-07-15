package ug.screw.servicios.eao.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ug.screw.servicios.eao.EAOBasico;
import ug.screw.servicios.modelos.seguridad.Rol;

@Stateless
@SuppressWarnings("unchecked")
public class RolEAO extends EAOBasico<Rol> {

	private static final String CONSULTA_TODOS = "Select r from Rol r";
	private static final String CONSULTA_POR_ESTADO = "Select r from Rol r Where r.estado = :estado";
	private static final String CONSULTA_POR_NOMBRE = "Select r from Rol r Where r.rol = :rol";

	public List<Rol> obtRol(){
		List<Rol> resultado = new ArrayList<Rol>();
		Query q = adminEntidad.createQuery(CONSULTA_TODOS);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<Rol> obtRolXEst(String estado){
		List<Rol> resultado = new ArrayList<Rol>();
		Query q = adminEntidad.createQuery(CONSULTA_POR_ESTADO);
		q.setParameter("estado", estado);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public Rol obtRol(String nombre){
		Query q = adminEntidad.createQuery(CONSULTA_POR_NOMBRE);
		q.setParameter("rol", nombre);
		try {
			return (Rol)q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}