package pe.gob.pj.hjudicial.restclient.reniec;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaConsultada implements Serializable {
	  	/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		private String tipoConsulta;
	    private String nroDocumentoIdentidad;
	    private String apellidoPaterno;
	    private String apellidoMaterno;
	    private String nombres;
	    private String nroDocumentoIdentidadApoderado;
	    private String vinculoApoderado;

}
