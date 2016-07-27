package ua.farmapriv.servicios.modelos.seguridad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: OpcionRol
 *
 */
@Entity
@Table(name="OPC_ROL")
@Audited(auditParents={
		EntidadBasica.class
})
public class OpcionRol extends EntidadBasica implements Serializable {
	
	private Long id;
	private Opcion opcion;
	private Rol rol;
	private static final long serialVersionUID = 1L;

	@Id    
	@SequenceGenerator(name="objGenIDOpcionRol", sequenceName="SEC_ID_OPC_ROL", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="objGenIDOpcionRol", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_OPCION_ROL")
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_OPCION")
	public Opcion getOpcion() {
		return this.opcion;
	}
	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}   

	@ManyToOne
	@JoinColumn(name="ID_ROL")
	public Rol getRol() {
		return this.rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
}
