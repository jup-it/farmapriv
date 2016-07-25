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
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="M_USUARIOS",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"usuario"})
	})
@Audited(auditParents={
		EntidadBasica.class
})
public class Usuario extends EntidadBasica implements Serializable {

	
	private Long id;
	private String usuario;
	private String clave;
	private String nombre;
	private String correo;
	private Boolean cambiarClave;
	private List<UsuarioRol> usuariosRoles;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="objGenIDUsuario", sequenceName="SEC_ID_USUARIO", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="objGenIDUsuario", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_USUARIO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}   
	
	public String getClave() {
		return this.clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}   
	
	public String getCorreo() {
		return this.correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}   

	@Column(name="CAMBIAR_CLAVE",length=1)
	public Boolean getCambiarClave() {
		return this.cambiarClave;
	}
	public void setCambiarClave(Boolean cambiarClave) {
		this.cambiarClave = cambiarClave;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@OneToMany(mappedBy="usuario")
	public List<UsuarioRol> getUsuariosRoles() {
		return usuariosRoles;
	}
	public void setUsuariosRoles(List<UsuarioRol> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}

}
