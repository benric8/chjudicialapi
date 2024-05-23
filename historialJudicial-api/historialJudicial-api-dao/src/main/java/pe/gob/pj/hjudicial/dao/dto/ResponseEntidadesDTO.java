package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;

@Data
public class ResponseEntidadesDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	private List<EntidadDTO> entidades;

	public ResponseEntidadesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseEntidadesDTO(String indicador, String mensaje, List<EntidadDTO> entidades) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
		this.entidades = entidades;
	}

}
