package ua.farmapriv.servicios.eao.seguridad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ua.farmapriv.servicios.eao.EAOBasico;
import ua.farmapriv.servicios.modelos.seguridad.Opcion;
import ua.farmapriv.servicios.modelos.seguridad.OpcionRol;
import ua.farmapriv.servicios.modelos.seguridad.Rol;
import ua.farmapriv.servicios.utilerias.NombresParametros;

@Stateless
@SuppressWarnings("unchecked")
public class OpcionRolEAO extends EAOBasico<OpcionRol> {

	private static final String CONSULTA_x_ROL = "Select x from OpcionRol x Where x.rol.rol = :rol and x.estado =:estado";
	private static final String CONSULTA_TODAS = "Select x from OpcionRol x Where x.estado :estado";
	private static final String CONSULTA_x_OPCROL = "Select x from OpcionRol x Where x.rol.id = :idRol and x.opcion.id = :idOpcion";
	private static final String CONSULTA_x_OPCPADRE = "Select count(x) from OpcionRol x Where x.rol.id = :idRol and x.opcion.opcionPadre.id = :idOpcion and x.estado = :estado";

	public List<OpcionRol> obtOpcXRol(Rol rol){
		List<OpcionRol> resultado = new ArrayList<OpcionRol>();
		Query q = adminEntidad.createQuery(CONSULTA_x_ROL);
		q.setParameter("rol", rol.getRol());
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public Long obtCanXPad(Rol rol, Opcion opcionPadre){
		Query q = adminEntidad.createQuery(CONSULTA_x_OPCPADRE);
		q.setParameter("idRol", rol.getId());
		q.setParameter("idOpcion", opcionPadre.getIdOpcion());
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return (Long)q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return new Long(0);
		}
	}
	
	public List<OpcionRol> obtOpcRolTod(){
		List<OpcionRol> resultado = new ArrayList<OpcionRol>();
		Query q = adminEntidad.createQuery(CONSULTA_TODAS);
		q.setParameter("estado", NombresParametros.ESTADO_ACTIVO);
		try {
			return q.getResultList();
		} catch (Exception e) {
			return resultado;
		}
	}
	
	public OpcionRol obtOpcRol(Rol rol, Opcion opcion){
		Query q = adminEntidad.createQuery(CONSULTA_x_OPCROL);
		q.setParameter("idRol", rol.getId());
		q.setParameter("idOpcion", opcion.getIdOpcion());
		try {
			return (OpcionRol)q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}