package pe.gob.pj.hjudicial.dao.dto.historialjudicial;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UsuarioDTO usuario;
	
	public LoginDTO(UsuarioDTO usuario) {
		super();
		this.usuario = usuario;
	}
	
}
