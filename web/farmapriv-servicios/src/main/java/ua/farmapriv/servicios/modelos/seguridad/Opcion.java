package ua.farmapriv.servicios.modelos.seguridad;

import java.io.Serializable;
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
 * Entity implementation class for Entity: Opcion
 *
 */
@Entity
@Table(name="M_OPCIONES")
@Audited(auditParents={
		EntidadBasica.class
})
public class Opcion extends EntidadBasica implements Serializable {

	
	private Long idOpcion;
	private Opcion opcionPadre;
	private Long orden;
	private String nombre;
	private String rutaAplicacion;
	private String icono;
	private List<Opcion> opcionesHijas;
	private List<OpcionRol> opcionesRoles;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="objGenIDOpcion", sequenceName="SEC_ID_OPCION", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="objGenIDOpcion", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_OPCION")
	public Long getIdOpcion() {
		return this.idOpcion;
	}
	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}

	@ManyToOne
	@JoinColumn(name="OPCION_PADRE")
	public Opcion getOpcionPadre() {
		return this.opcionPadre;
	}
	public void setOpcionPadre(Opcion opcionPadre) {
		this.opcionPadre = opcionPadre;
	}

	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="RUTA_APLICACION")
	public String getRutaAplicacion() {
		return this.rutaAplicacion;
	}
	public void setRutaAplicacion(String rutaAplicacion) {
		this.rutaAplicacion = rutaAplicacion;
	}
	
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
	
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	@OneToMany(mappedBy="opcionPadre")
	public List<Opcion> getOpcionesHijas() {
		return opcionesHijas;
	}
	public void setOpcionesHijas(List<Opcion> opcionesHijas) {
		this.opcionesHijas = opcionesHijas;
	}
	
	@OneToMany(mappedBy="rol")
	public List<OpcionRol> getOpcionesRoles() {
		return opcionesRoles;
	}
	public void setOpcionesRoles(List<OpcionRol> opcionesRoles) {
		this.opcionesRoles = opcionesRoles;
	}
   
}
