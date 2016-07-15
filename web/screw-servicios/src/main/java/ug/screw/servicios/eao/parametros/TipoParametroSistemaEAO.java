package ug.screw.servicios.eao.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ug.screw.servicios.eao.EAOBasico;
import ug.screw.servicios.modelos.parametros.TipoParametroSistema;

@Stateless
@SuppressWarnings("unchecked")
public class TipoParametroSistemaEAO extends EAOBasico<TipoParametroSistema> {

	private static final String CONSULTA_TODOS = "Select o from TipoParametroSistema o";

	public List<TipoParametroSistema> obtTipParam(){
		List<TipoParametroSistema> resultado = new ArrayList<TipoParametroSistema>();
		Query q = adminEntidad.createQuery(CONSULTA_TODOS);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
}
