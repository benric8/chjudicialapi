package pe.gob.pj.hjudicial.dao.dto.historialjudicial;

import java.io.Serializable;

import lombok.Data;

@Data
public class OpcionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String etiqueta;
	private String url;
	private String icono;
	private String activo;
	
	public OpcionDTO(String etiqueta, String url, String icono, String activo) {
		super();
		this.etiqueta = etiqueta;
		this.url = url;
		this.icono = icono;
		this.activo = activo;
	}
	
}
