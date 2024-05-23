package pe.gob.pj.hjudicial.dao.dto.historialjudicial;

import java.io.Serializable;

import lombok.Data;

@Data
public class EntidadDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String P_ENTIDAD = "entidadDTO";
	public static final String P_ID_ENTIDAD = "idEntidadDTO";
	public static final String P_ACTIVO_ENTIDAD = "activoEntidadDTO";
	
	private int id;
	private String nombreEntidad;
	private String numeroDocumento;
	private String activo;
	
}
