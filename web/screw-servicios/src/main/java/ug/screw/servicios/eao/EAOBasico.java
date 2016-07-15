package ug.screw.servicios.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ug.screw.servicios.utilerias.NombresParametros;

public class EAOBasico<E extends Serializable> {

	@PersistenceContext(unitName=NombresParametros.NOMBRE_UNIDAD_PERSISTENCIA)
	protected EntityManager adminEntidad;

	public void crear(E entidad){
		adminEntidad.persist(entidad);
	}
	
	@SuppressWarnings("unchecked")
	public E refrescar(E entidad, Object id){
		if(adminEntidad.contains(entidad)){
			adminEntidad.refresh(entidad);
			return entidad;
		}else{
			return (E)adminEntidad.find(entidad.getClass(), id);
		}
	}
	
	public E actualizar(E entidad){
		return adminEntidad.merge(entidad);
	}
	
	public void eliminar(E entidad){
		adminEntidad.remove(entidad);
		adminEntidad.flush();
	}
	
	@SuppressWarnings("unchecked")
	public E buscarPorId(Class<?> clase, Object id){
		return (E)adminEntidad.find(clase, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> obtenerActivos(Class<?> clase){
		List<E> resultado = new ArrayList<E>();
		Query q = adminEntidad.createQuery(
				"Select e from " + clase.getName() + " e Where e.estado = :estado");
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<E> obtenerTodos(Class<?> clase){
		List<E> resultado = new ArrayList<E>();
		Query q = adminEntidad.createQuery(
				"Select e from " + clase.getName() + " e");
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
}
