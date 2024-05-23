package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.PerfilDTO;

@Data
public class ResponsePerfilesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	private List<PerfilDTO> perfiles;

	public ResponsePerfilesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponsePerfilesDTO(String indicador, String mensaje, List<PerfilDTO> perfiles) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
		this.perfiles = perfiles;
	}
}
