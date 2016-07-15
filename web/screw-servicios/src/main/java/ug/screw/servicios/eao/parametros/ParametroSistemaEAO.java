package ug.screw.servicios.eao.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ug.screw.servicios.eao.EAOBasico;
import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.modelos.parametros.TipoParametroSistema;
import ug.screw.servicios.utilerias.NombresParametros;

@Stateless
@SuppressWarnings("unchecked")
public class ParametroSistemaEAO extends EAOBasico<ParametroSistema> {

	private static final String CONSULTA_x_ESTADO = "Select p from ParametroSistema p where p.estado = :estado";
	private static final String CONSULTA_x_TIPO = "Select p from ParametroSistema p where p.estado = :estado and p.tipoParametro.id = :idTipoParametro";
	private static final String CONSULTA_x_NOMBRE = "Select p from ParametroSistema p where p.estado = :estado and p.nombre = :nombreParametro";

	public List<ParametroSistema> obtParam(){
		List<ParametroSistema> resultado = new ArrayList<ParametroSistema>();
		Query q = adminEntidad.createQuery(CONSULTA_x_ESTADO);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public List<ParametroSistema> obtParam(TipoParametroSistema tipoParametro){
		List<ParametroSistema> resultado = new ArrayList<ParametroSistema>();
		Query q = adminEntidad.createQuery(CONSULTA_x_TIPO);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		q.setParameter("idTipoParametro", tipoParametro.getId());
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public ParametroSistema obtParam(String nombre){
		Query q = adminEntidad.createQuery(CONSULTA_x_NOMBRE);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		q.setParameter("nombreParametro", nombre);
		try {
			return (ParametroSistema)q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
