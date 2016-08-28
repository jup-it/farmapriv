package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: Proveedor
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "PRV")
public class Proveedor extends EntidadBasica implements Serializable {

	private Long id;
	private String descripcion;
	private String identificacion;
	private BigDecimal calificacion;

	private List<ProductoProveedor> listaProdProv;
	private List<OrdenCompra> listaOrdenCompra;

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDPrv", sequenceName = "SEC_ID_PRV", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDPrv", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PRV")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(nullable = false)
	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public BigDecimal getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	@OneToMany(mappedBy = "proveedor")
	public List<ProductoProveedor> getListaProdProv() {
		return listaProdProv;
	}

	public void setListaProdProv(List<ProductoProveedor> listaProdProv) {
		this.listaProdProv = listaProdProv;
	}

	@OneToMany(mappedBy = "proveedor")
	public List<OrdenCompra> getListaOrdenCompra() {
		return listaOrdenCompra;
	}

	public void setListaOrdenCompra(List<OrdenCompra> listaOrdenCompra) {
		this.listaOrdenCompra = listaOrdenCompra;
	}
}
