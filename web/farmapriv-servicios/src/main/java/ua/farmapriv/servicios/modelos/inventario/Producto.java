package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
@Table(name="PRO")
public class Producto extends EntidadBasica implements Serializable {

	
	private Long id;
	private String codigo;
	private String descripcion;
	private CategoriaProducto categoriaProducto;
	private BigDecimal stock;
	private static final long serialVersionUID = 1L;

	@Id    
	@SequenceGenerator(name="genIDProd", sequenceName="SEC_ID_PRO", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="genIDProd", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PRO")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false,unique=true)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	@JoinColumn(name="ID_CAT_PRO")
	public CategoriaProducto getCategoriaProducto() {
		return this.categoriaProducto;
	}
	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public BigDecimal getStock() {
		return this.stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

}