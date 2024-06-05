package pe.gob.pj.hjudicial.restclient.reniec;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestConsultarPersona implements Serializable {
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		private String formatoRespuesta;
	    private String numeroDocumentoIdentidad;
	    private String motivo;
	    private String codigoAplicativo;
	    private Auditoria auditoria;
}
