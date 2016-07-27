package ua.farmapriv.servicios.modelos.parametros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: Parametro
 *
 */
@Entity
@Table(name="PAR_SIS")
@Audited(
	auditParents={
		EntidadBasica.class
	},
	targetAuditMode=RelationTargetAuditMode.NOT_AUDITED
)
public class ParametroSistema extends EntidadBasica implements Serializable {
	
	private Long id;
	private String nombre;
	private String descripcion;
	private String valor;
	private TipoParametroSistema tipoParametro;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="genIDParamSis", sequenceName="SEC_ID_PAR_SIS", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="genIDParamSis", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PARAM_SISTEMA")
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

	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}   

	public String getValor() {
		return this.valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name="ID_TIPO_PARAM_SIS")
	public TipoParametroSistema getTipoParametro() {
		return tipoParametro;
	}
	public void setTipoParametro(TipoParametroSistema tipoParametro) {
		this.tipoParametro = tipoParametro;
	}
}
