package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;
import ua.farmapriv.servicios.modelos.inventario.Producto;

/**
 * Entity implementation class for Entity: PrecioProducto
 *
 */
@Entity
@Table(name="PRE_PRO")

public class PrecioProducto extends EntidadBasica implements Serializable {
	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal valor;
	private Producto producto;
	private static final long serialVersionUID = 1L;

	@Id    
	@SequenceGenerator(name="genIDPreProd", sequenceName="SEC_ID_PRE_PRO", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="genIDPreProd", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PRO")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}   

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFechaFin() {
		return this.fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getValor() {
		return this.valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name="ID_PRO")
	public Producto getProducto() {
		return this.producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
   
}
