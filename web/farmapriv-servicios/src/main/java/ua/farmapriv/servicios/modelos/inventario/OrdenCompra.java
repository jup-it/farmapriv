package ua.farmapriv.servicios.modelos.inventario;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import ua.farmapriv.servicios.modelos.EntidadBasica;
import ua.farmapriv.servicios.modelos.inventario.Proveedor;

/**
 * Entity implementation class for Entity: OrdenCompra
 * 
 * @author mesterilla
 * @version 1.0
 */
@Entity
@Table(name = "ODC")
public class OrdenCompra extends EntidadBasica implements Serializable {

	private Long id;
	private Proveedor proveedor;
	private Date fechaIniVigencia;
	private Date fechaFinVigencia;
	private String estadoOrden;
	private List<DetOrdenCompra> listaDetOrdenCompra;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "genIDOC", sequenceName = "SEC_ID_ODC", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "genIDOC", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_ODC")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PRV", nullable = false)
	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date getFechaIniVigencia() {
		return this.fechaIniVigencia;
	}

	public void setFechaIniVigencia(Date fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date getFechaFinVigencia() {
		return this.fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	@Column(nullable = false)
	public String getEstadoOrden() {
		return this.estadoOrden;
	}

	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	@OneToMany(mappedBy = "ordenCompra")
	public List<DetOrdenCompra> getListaDetOrdenCompra() {
		return listaDetOrdenCompra;
	}

	public void setListaDetOrdenCompra(List<DetOrdenCompra> listaDetOrdenCompra) {
		this.listaDetOrdenCompra = listaDetOrdenCompra;
	}

}