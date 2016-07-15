package ug.screw.servicios.modelos.parametros;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TipoParametro
 *
 */
@Entity
@Table(name="TIPOS_PARAM_SIS")
public class TipoParametroSistema implements Serializable {

	
	private Long id;
	private String nombre;
	private String observacion;
	private List<ParametroSistema> listaParametros;
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_PARAMETRO")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}   

	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   

	public String getObservacion() {
		return this.observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@OneToMany(mappedBy="tipoParametro")
	public List<ParametroSistema> getListaParametros() {
		return listaParametros;
	}
	public void setListaParametros(List<ParametroSistema> listaParametros) {
		this.listaParametros = listaParametros;
	}
}