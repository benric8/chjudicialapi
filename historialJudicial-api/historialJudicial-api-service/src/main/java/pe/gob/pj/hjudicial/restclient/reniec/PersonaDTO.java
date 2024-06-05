package pe.gob.pj.hjudicial.restclient.reniec;



import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String primerApellido;
    private String segundoApellido;
    private String nombres;
    private String estadoCivil;
    private String fotoB64;
    private String ubigeo;
    private String direccion;
    private String restriccion;

    
}

