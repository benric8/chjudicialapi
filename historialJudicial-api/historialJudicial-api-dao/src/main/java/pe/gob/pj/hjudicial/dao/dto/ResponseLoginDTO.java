package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;

@Data
public class ResponseLoginDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	private UsuarioDTO usuario;

	public ResponseLoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseLoginDTO(String indicador, String mensaje, UsuarioDTO usuario) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
		this.usuario = usuario;
	}

}
