package pe.gob.pj.hjudicial.service;

public interface SeguridadService {

	public String autenticarUsuario(String codigoCliente, String codigoRol, String usuario, String clave, String cuo) throws Exception;
	
	public boolean validarAccesoMetodo(String usuario, String rol, String ruta, String cuo) throws Exception;
	
}
