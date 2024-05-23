package pe.gob.pj.hjudicial.dao.dto.seguridad;

import java.io.Serializable;

public class MaeRol implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Integer id;		

	private String cRol;

	private String xNombre;

	private String lActivo;
	
	public MaeRol() {
		super();
	}

	public MaeRol(Integer id, String cRol, String xNombre, String lActivo) {
		super();
		this.id = id;
		this.cRol = cRol;
		this.xNombre = xNombre;
		this.lActivo = lActivo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getcRol() {
		return cRol;
	}

	public void setcRol(String cRol) {
		this.cRol = cRol;
	}

	public String getxNombre() {
		return xNombre;
	}

	public void setxNombre(String xNombre) {
		this.xNombre = xNombre;
	}

	public String getlActivo() {
		return lActivo;
	}

	public void setlActivo(String lActivo) {
		this.lActivo = lActivo;
	}		
	
}
