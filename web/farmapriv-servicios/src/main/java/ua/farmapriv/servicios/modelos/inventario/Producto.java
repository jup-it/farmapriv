package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: Product
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "PRO")
@Audited
public class Producto extends EntidadBasica implements Serializable {

	private Long id;

	private String codigo;
	private String descripcion;

	private BigDecimal stock;
	private BigDecimal precio;
	private BigDecimal costo;
	private BigDecimal cantidadEmpaque;

	private CategoriaProducto categoriaProducto;
	private Marca marca;

	private List<ProductoProveedor> listaProdProv;
	private List<DetOrdenCompra> listaDetOrdenCompra;
	private List<Kardex> listaKardex;

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDProd", sequenceName = "SEC_ID_PRO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDProd", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PRO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(nullable = false)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	@JoinColumn(name = "ID_CAT_PRO")
	public CategoriaProducto getCategoriaProducto() {
		return this.categoriaProducto;
	}

	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	@Column(nullable = false)
	public BigDecimal getStock() {
		return this.stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	@Column(nullable = false)
	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	@Column(nullable = false)
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	@Column(nullable = false)
	public BigDecimal getCantidadEmpaque() {
		return cantidadEmpaque;
	}

	public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
		this.cantidadEmpaque = cantidadEmpaque;
	}

	@ManyToOne
	@JoinColumn(name = "ID_MAR", nullable = false)
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@OneToMany(mappedBy = "producto")
	public List<ProductoProveedor> getListaProdProv() {
		return listaProdProv;
	}

	public void setListaProdProv(List<ProductoProveedor> listaProdProv) {
		this.listaProdProv = listaProdProv;
	}

	@OneToMany(mappedBy = "producto")
	public List<DetOrdenCompra> getListaDetOrdenCompra() {
		return listaDetOrdenCompra;
	}

	public void setListaDetOrdenCompra(List<DetOrdenCompra> listaDetOrdenCompra) {
		this.listaDetOrdenCompra = listaDetOrdenCompra;
	}

	@OneToMany(mappedBy = "producto")
	public List<Kardex> getListaKardex() {
		return listaKardex;
	}

	public void setListaKardex(List<Kardex> listaKardex) {
		this.listaKardex = listaKardex;
	}
}