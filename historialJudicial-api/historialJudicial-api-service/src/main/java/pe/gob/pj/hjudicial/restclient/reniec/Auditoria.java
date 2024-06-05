package pe.gob.pj.hjudicial.restclient.reniec;

import java.io.Serializable;

import lombok.Data;

@Data
public class Auditoria implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuario;
    private String nombrePc;
    private String numeroIp;
    private String direccionMac;
}
