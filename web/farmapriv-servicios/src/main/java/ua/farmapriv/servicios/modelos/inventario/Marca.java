package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
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
 * Entity implementation class for Entity: Marca
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "MAR")
public class Marca extends EntidadBasica implements Serializable {

	private Long id;
	private String descripcion;
	private List<Producto> listaProductos;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDMar", sequenceName = "SEC_ID_MAR", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDMar", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_MAR")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(mappedBy = "marca")
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
}
