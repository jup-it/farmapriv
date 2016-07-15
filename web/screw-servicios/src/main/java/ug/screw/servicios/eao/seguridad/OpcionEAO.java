package ug.screw.servicios.eao.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ug.screw.servicios.eao.EAOBasico;
import ug.screw.servicios.modelos.seguridad.Opcion;
import ug.screw.servicios.utilerias.NombresParametros;

@Stateless
@SuppressWarnings("unchecked")
public class OpcionEAO extends EAOBasico<Opcion> {
	
	private static final String CONSULTA_POR_ESTADO = "Select o from Opcion o Where o.estado = :estado";
	private static final String CONSULTA_PADRES = "Select o from Opcion o Where o.opcionPadre is null and o.estado = :estado";
	private static final String CONSULTA_HIJAS = "Select o from Opcion o Where o.opcionPadre is not null and o.estado = :estado";
	private static final String CONSULTA_HIJAS_x_PADRE = "Select o from Opcion o Where o.opcionPadre.id = :idOpcionPadre and o.estado = :estado";

	public List<Opcion> obtOpcXEst(String estado){
		List<Opcion> resultado = new ArrayList<Opcion>();
		Query q = adminEntidad.createQuery(CONSULTA_POR_ESTADO);
		q.setParameter("estado", estado);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}

	public List<Opcion> obtOpcPad(){
		List<Opcion> resultado = new ArrayList<Opcion>();
		Query q = adminEntidad.createQuery(CONSULTA_PADRES);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<Opcion> obtOpcHij(Opcion opcionPadre){
		List<Opcion> resultado = new ArrayList<Opcion>();
		Query q = adminEntidad.createQuery(CONSULTA_HIJAS_x_PADRE);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		q.setParameter("idOpcionPadre", opcionPadre.getIdOpcion());
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<Opcion> obtOpcTerm(){
		List<Opcion> resultado = new ArrayList<Opcion>();
		Query q = adminEntidad.createQuery(CONSULTA_HIJAS);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
}