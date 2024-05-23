package pe.gob.pj.hjudicial.dao.dto;

import java.util.List;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;

@Data
public class ResponseUsuariosDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	private List<UsuarioDTO> usuarios;

	public ResponseUsuariosDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUsuariosDTO(String indicador, String mensaje, List<UsuarioDTO> usuarios) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
		this.usuarios = usuarios;
	}

}
