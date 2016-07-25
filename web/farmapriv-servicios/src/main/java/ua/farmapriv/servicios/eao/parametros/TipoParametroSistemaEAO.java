package ua.farmapriv.servicios.eao.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ua.farmapriv.servicios.eao.EAOBasico;
import ua.farmapriv.servicios.modelos.parametros.TipoParametroSistema;

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
