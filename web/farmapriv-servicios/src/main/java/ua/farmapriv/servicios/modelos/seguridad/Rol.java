package ua.farmapriv.servicios.modelos.seguridad;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

import ua.farmapriv.servicios.modelos.EntidadBasica;

/**
 * Entity implementation class for Entity: Rol
 *
 */
@Entity
@Table(name="M_ROLES",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"rol"})
	})
@Audited(auditParents={
		EntidadBasica.class
})
public class Rol extends EntidadBasica implements Serializable {

	
	private Long id;
	private String rol;
	private List<UsuarioRol> usuariosRoles;
	private List<OpcionRol> opcionesRoles;
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="objGenIDRol", sequenceName="SEC_ID_ROL", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="objGenIDRol", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_ROL")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}   
	
	@OneToMany(mappedBy="rol")
	public List<OpcionRol> getOpcionesRoles() {
		return opcionesRoles;
	}
	public void setOpcionesRoles(List<OpcionRol> opcionesRoles) {
		this.opcionesRoles = opcionesRoles;
	}
	
	@OneToMany(mappedBy="rol")
	public List<UsuarioRol> getUsuariosRoles() {
		return usuariosRoles;
	}
	public void setUsuariosRoles(List<UsuarioRol> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}
   
}
