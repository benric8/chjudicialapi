package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseRespuestaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	
	public ResponseRespuestaDTO(String indicador, String mensaje) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
	}

	public ResponseRespuestaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
