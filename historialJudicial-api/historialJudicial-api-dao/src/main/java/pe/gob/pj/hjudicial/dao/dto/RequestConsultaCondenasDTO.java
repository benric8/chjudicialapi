package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestConsultaCondenasDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String documento;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	
	private String tipoConsulta;

}
