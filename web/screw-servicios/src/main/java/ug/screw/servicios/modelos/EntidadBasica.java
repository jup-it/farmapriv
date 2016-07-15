package ug.screw.servicios.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ug.screw.servicios.modelos.seguridad.Usuario;

@MappedSuperclass
public class EntidadBasica implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String estado;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private Usuario usuarioModificacion;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CREACION")
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}   

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ACTUALIZACION")
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_MOD")
	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

}
