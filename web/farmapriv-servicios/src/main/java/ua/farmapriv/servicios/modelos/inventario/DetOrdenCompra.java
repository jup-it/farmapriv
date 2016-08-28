package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;
import ua.farmapriv.servicios.modelos.inventario.OrdenCompra;
import ua.farmapriv.servicios.modelos.inventario.Producto;

/**
 * Entity implementation class for Entity: DetOrdenCompra
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "DOC")
public class DetOrdenCompra extends EntidadBasica implements Serializable {

	private Long id;
	private OrdenCompra ordenCompra;
	private BigDecimal cantidadSolicitada;
	private BigDecimal costoActual;
	private Producto producto;
	private BigDecimal cantidadRecibida;
	private String estadoEntrega;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDDOC", sequenceName = "SEC_ID_DOC", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDDOC", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_DOC")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "ID_ODC", nullable = false)
	public OrdenCompra getOrdenCompra() {
		return this.ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	@Column(nullable = false)
	public BigDecimal getCantidadSolicitada() {
		return this.cantidadSolicitada;
	}

	public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public BigDecimal getCostoActual() {
		return this.costoActual;
	}

	public void setCostoActual(BigDecimal costoActual) {
		this.costoActual = costoActual;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PRO", nullable = false)
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getCantidadRecibida() {
		return this.cantidadRecibida;
	}

	public void setCantidadRecibida(BigDecimal cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	@Column(nullable = false)
	public String getEstadoEntrega() {
		return this.estadoEntrega;
	}

	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

}