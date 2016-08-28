package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.math.BigDecimal;
import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;
import ua.farmapriv.servicios.modelos.inventario.Producto;
import ua.farmapriv.servicios.modelos.inventario.Proveedor;

/**
 * Entity implementation class for Entity: ProductoProveedor
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "PRO_PRV")
public class ProductoProveedor extends EntidadBasica implements Serializable {

	private Long id;
	private Producto producto;
	private Proveedor proveedor;
	private BigDecimal prioridad;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDProPrv", sequenceName = "SEC_ID_PRO_PRV", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDProPrv", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PRO_PRV")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PRO", nullable = false)
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PRV", nullable = false)
	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public BigDecimal getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(BigDecimal prioridad) {
		this.prioridad = prioridad;
	}

}