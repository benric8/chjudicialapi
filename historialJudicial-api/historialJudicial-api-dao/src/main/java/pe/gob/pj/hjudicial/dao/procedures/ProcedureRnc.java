package pe.gob.pj.hjudicial.dao.procedures;

import java.io.Serializable;

public class ProcedureRnc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//public static final String QUERY_CONSULTAR_CONDENAS_DNI="exec uspc_rep_certificado_dni(?)";
	public static final String QUERY_CONSULTAR_CONDENAS_DNI="exec uspw_WsFinanciamientoProhibido(?,?,?,?,?)";

	//public static final String QUERY_CONSULTAR_CONDENAS_NOMBRES="exec uspc_rep_certificado_nombre(?,?,?)";
	public static final String QUERY_CONSULTAR_CONDENAS_NOMBRES="exec uspw_WsFinanciamientoProhibido(?,?,?,?,?)";

}
