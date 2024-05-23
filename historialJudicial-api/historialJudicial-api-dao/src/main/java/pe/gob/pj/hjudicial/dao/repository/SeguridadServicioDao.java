package pe.gob.pj.hjudicial.dao.repository;

import java.util.List;

import pe.gob.pj.hjudicial.dao.dto.seguridad.MovUsuario;
import pe.gob.pj.hjudicial.dao.entity.seguridad.MaeRol;

public interface SeguridadServicioDao {
	
	public String autenticarUsuario(String codigoCliente, String codigoRol, String usuario, String clave, String cuo) throws Exception;
	
	public MovUsuario recuperaInfoUsuario(String id,String cuo) throws Exception;
	
	public  List<MaeRol> recuperarRoles(String id,String cuo) throws Exception;
	
	public boolean validarAccesoMetodo(String usuario, String rol, String operacion, String cuo) throws Exception;

}
