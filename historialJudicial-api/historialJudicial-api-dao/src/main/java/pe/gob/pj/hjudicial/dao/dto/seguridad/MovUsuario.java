package pe.gob.pj.hjudicial.dao.dto.seguridad;

import java.io.Serializable;

public class MovUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Integer id;		

	private String cUsuario;

	private String cClave;	

	private String lActivo;
		
	public MovUsuario() {
		super();
	}

	public MovUsuario(Integer id, String cUsuario, String cClave, String lActivo) {
		super();
		this.id = id;
		this.cUsuario = cUsuario;
		this.cClave = cClave;
		this.lActivo = lActivo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getcUsuario() {
		return cUsuario;
	}

	public void setcUsuario(String cUsuario) {
		this.cUsuario = cUsuario;
	}

	public String getcClave() {
		return cClave;
	}

	public void setcClave(String cClave) {
		this.cClave = cClave;
	}

	public String getlActivo() {
		return lActivo;
	}

	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}	
}
