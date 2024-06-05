package pe.gob.pj.hjudicial.restclient.reniec;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReniecResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private PersonaDTO data;
	private String codigoOperacion;
	
}
