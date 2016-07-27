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
 * Entity implementation class for Entity: UsuarioRol
 *
 */
@Entity
@Table(name="USU_ROL")
@Audited(auditParents={
		EntidadBasica.class
})
public class UsuarioRol extends EntidadBasica implements Serializable {

	
	private Long id;
	private Rol rol;
	private Usuario usuario;
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="objGenIDUsuarioRol", sequenceName="SEC_ID_USU_ROL", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="objGenIDUsuarioRol", strategy=GenerationType.SEQUENCE)
	@Column(name="ID_USUARIO_ROL")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_ROL")
	public Rol getRol() {
		return this.rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
