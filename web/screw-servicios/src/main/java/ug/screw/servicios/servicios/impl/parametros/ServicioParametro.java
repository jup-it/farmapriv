package ug.screw.servicios.servicios.impl.parametros;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ug.screw.servicios.modelos.parametros.ParametroSistema;
import ug.screw.servicios.modelos.parametros.TipoParametroSistema;
import ug.screw.servicios.servicios.parametros.IServicioParametro;
import ug.screw.servicios.eao.parametros.ParametroSistemaEAO;
import ug.screw.servicios.eao.parametros.TipoParametroSistemaEAO;

@Stateless
public class ServicioParametro implements IServicioParametro {

	@EJB
	private TipoParametroSistemaEAO tipoParametroEAO;
	
	@EJB
	private ParametroSistemaEAO parametroEAO;
	
	@Override
	public TipoParametroSistema refTipParam(TipoParametroSistema entidad) {
		return tipoParametroEAO.refrescar(entidad, entidad.getId());
	}

	@Override
	public List<TipoParametroSistema> obtTipParam() {
		return tipoParametroEAO.obtTipParam();
	}

	@Override
	public void crearParam(ParametroSistema entidad) {
		parametroEAO.crear(entidad);
	}

	@Override
	public ParametroSistema actParam(ParametroSistema entidad) {
		return parametroEAO.actualizar(entidad);
	}

	@Override
	public ParametroSistema refParam(ParametroSistema entidad) {
		return parametroEAO.refrescar(entidad, entidad.getId());
	}

	@Override
	public ParametroSistema obtParam(String nombre){
		return parametroEAO.obtParam(nombre);
	}
	
	@Override
	public List<ParametroSistema> obtParam() {
		return parametroEAO.obtParam();
	}

	@Override
	public List<ParametroSistema> obtParam(TipoParametroSistema tipoParametro) {
		return parametroEAO.obtParam(tipoParametro);
	}

}