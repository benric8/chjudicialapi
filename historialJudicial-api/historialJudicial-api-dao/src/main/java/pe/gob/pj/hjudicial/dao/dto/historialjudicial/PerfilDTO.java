package pe.gob.pj.hjudicial.dao.dto.historialjudicial;

import java.io.Serializable;

import lombok.Data;

@Data
public class PerfilDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String rol;
	private String perfil;
	private String descripcion;
	private String activo;
}
