package pe.gob.pj.hjudicial.service;

import java.util.Map;

import pe.gob.pj.hjudicial.dao.dto.ResponseLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseRespuestaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseUsuariosDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;

public interface UsuarioService {

	public ResponseLoginDTO login(String cuo, String cUsuario, String clave) throws Exception;
	
	public ResponseRespuestaDTO cambiarClave(String cuo, String cUsuario, String claveActual, String claveNueva) throws Exception;
	public boolean recuperarClave(String cuo, int idUsuario) throws Exception;
	public ResponseUsuariosDTO obtenerUsuarios(String cuo, Map<String, Object> filters) throws Exception;
	public ResponseUsuariosDTO crearUsuario(String cuo, UsuarioDTO usuarioI) throws Exception;
	public void actualizarUsuario(String cuo, UsuarioDTO usuarioU) throws Exception;
	
}
