package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: CategoriaProducto
 *
 */
@Entity
@Table(name="CAT_PROD")
public class CategoriaProducto extends EntidadBasica implements Serializable {
	private Long id;
	private String descripcion;
	private CategoriaProducto categoriaPadre;
	private List<CategoriaProducto> categoriasHijas;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="genIDCatProd", sequenceName="SEC_ID_CAT_PROD", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="genIDCatProd", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_CAT_PROD")
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

	@ManyToOne
	@JoinColumn(name="ID_CAT_PADRE")
	public CategoriaProducto getCategoriaPadre() {
		return categoriaPadre;
	}
	public void setCategoriaPadre(CategoriaProducto categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	@OneToMany(mappedBy="categoriaPadre")
	public List<CategoriaProducto> getCategoriasHijas() {
		return categoriasHijas;
	}
	public void setCategoriasHijas(List<CategoriaProducto> categoriasHijas) {
		this.categoriasHijas = categoriasHijas;
	}  
}
