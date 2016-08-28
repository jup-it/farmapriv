package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;
import ua.farmapriv.servicios.modelos.inventario.Producto;

/**
 * Entity implementation class for Entity: Kardex
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "KDX")
public class Kardex extends EntidadBasica implements Serializable {

	private Long id;
	private String razon;	
	private String tipoMovimiento;
	
	private BigDecimal cantidad;
	private BigDecimal costo;	
	private BigDecimal saldoCantidad;
	private BigDecimal saldoCosto;
	
	private Producto producto;
	private DetOrdenCompra detOrdenCompra;
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDKDX", sequenceName = "SEC_ID_KDX", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDKDX", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_KDX")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false)
	public String getRazon() {
		return this.razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	@ManyToOne
	@JoinColumn(name="ID_PRO", nullable=false)
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Column(nullable=false)
	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	@Column(nullable=false)
	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	@Column(nullable=false)
	public String getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	@Column(nullable=false)
	public BigDecimal getSaldoCantidad() {
		return this.saldoCantidad;
	}

	public void setSaldoCantidad(BigDecimal saldoCantidad) {
		this.saldoCantidad = saldoCantidad;
	}

	@Column(nullable=false)
	public BigDecimal getSaldoCosto() {
		return this.saldoCosto;
	}

	public void setSaldoCosto(BigDecimal saldoCosto) {
		this.saldoCosto = saldoCosto;
	}

	@OneToOne
	@JoinColumns({
		@JoinColumn(name="ID_ODC",referencedColumnName="ID_ODC"),
		@JoinColumn(name="ID_DOC",referencedColumnName="ID_DOC")
	})
	public DetOrdenCompra getDetOrdenCompra() {
		return detOrdenCompra;
	}
	public void setDetOrdenCompra(DetOrdenCompra detOrdenCompra) {
		this.detOrdenCompra = detOrdenCompra;
	}
}
