package ua.farmapriv.servicios.servicios.parametros;

import java.util.List;

import javax.ejb.Remote;

import ua.farmapriv.servicios.modelos.parametros.ParametroSistema;
import ua.farmapriv.servicios.modelos.parametros.TipoParametroSistema;

@Remote
public interface IServicioParametro {

	public TipoParametroSistema refTipParam(TipoParametroSistema entidad);
	public List<TipoParametroSistema> obtTipParam();
	public void crearParam(ParametroSistema entidad);
	public ParametroSistema actParam(ParametroSistema entidad);
	public ParametroSistema refParam(ParametroSistema entidad);
	public ParametroSistema obtParam(String nombre);
	public List<ParametroSistema> obtParam();
	public List<ParametroSistema> obtParam(TipoParametroSistema tipoParametro);
}
