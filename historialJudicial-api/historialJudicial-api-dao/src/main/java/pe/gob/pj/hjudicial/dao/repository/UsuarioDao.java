package pe.gob.pj.hjudicial.dao.repository;

import java.util.Map;

import pe.gob.pj.hjudicial.dao.dto.ResponseLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseRespuestaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseUsuariosDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;

public interface UsuarioDao {
	
	public ResponseLoginDTO login(String cuo, String usuario) throws Exception;

	public ResponseRespuestaDTO cambiarClave(String cuo, int idUsuario, String claveNueva) throws Exception;
	public ResponseRespuestaDTO recuperarClave(String cuo, int idUsuario) throws Exception;
	public ResponseUsuariosDTO obtenerUsuarios(String cuo, Map<String, Object> filters) throws Exception;
	public void crearUsuario(String cuo, UsuarioDTO usuarioI) throws Exception;
	public void actualizarUsuario(String cuo, UsuarioDTO usuarioU) throws Exception;
	public boolean existeUsuario(String cuo, String usuario) throws Exception;
}
