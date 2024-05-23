package pe.gob.pj.hjudicial.dao.dto;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ResponseTokenDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Setter @Getter
	private String codigo;
	
	@Setter @Getter
	private String descripcion;
	
	@Setter @Getter
	private String token;

}
