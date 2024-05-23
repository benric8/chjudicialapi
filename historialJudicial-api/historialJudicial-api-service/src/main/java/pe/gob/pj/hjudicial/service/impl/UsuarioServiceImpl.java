package pe.gob.pj.hjudicial.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.hjudicial.dao.dto.ResponseLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseRespuestaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseUsuariosDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;
import pe.gob.pj.hjudicial.dao.repository.UsuarioDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.service.UsuarioService;

/**
 * Objeto     : UsuarioServiceImpl.
 * Descripción: Clase que implementa las operaciones de usuario.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = true, rollbackFor = {Exception.class, SQLException.class})
	public ResponseLoginDTO login(String cuo, String usuario, String clave) throws Exception {
		
		ResponseLoginDTO loginResponse;
		try {

			String password = EncryptUtils.cryptBase64u(clave, Cipher.ENCRYPT_MODE);
			loginResponse = usuarioDao.login(cuo, usuario);
			
			if(loginResponse.getUsuario()!=null) {
				if(UtilsProject.isNull(loginResponse.getUsuario().getContrasenia()).equals(password)) {
					loginResponse.getUsuario().setContrasenia("********");
				}else {
					loginResponse.setIndicador(ConstantesProject.RPTA_0);
					loginResponse.setMensaje("Las credenciales ingresadas son incorrectas.");
					loginResponse.setUsuario(null);
				}
			} 
			return loginResponse;
			
		} catch (Exception e) {
			logger.error("{} Error service login: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public ResponseRespuestaDTO cambiarClave(String cuo, String usuario, String claveActual, String claveNueva) throws Exception {

		ResponseRespuestaDTO responseRespuesta = new ResponseRespuestaDTO();
		try {
			String password = EncryptUtils.cryptBase64u(claveActual, Cipher.ENCRYPT_MODE);
			ResponseLoginDTO loginResponse = usuarioDao.login(cuo, usuario);
			if(loginResponse.getUsuario()!=null) {
				if(UtilsProject.isNull(loginResponse.getUsuario().getContrasenia()).equals(password)) {
					responseRespuesta = usuarioDao.cambiarClave(cuo, loginResponse.getUsuario().getId(), EncryptUtils.cryptBase64u(claveNueva, Cipher.ENCRYPT_MODE));
				}else {
					responseRespuesta.setIndicador(ConstantesProject.RPTA_0);
					responseRespuesta.setMensaje("La contraseña actual es incorrecta.");
				}
			}else {
				responseRespuesta.setIndicador(ConstantesProject.RPTA_0);
				responseRespuesta.setMensaje("El usuario no existe o está inactivo.");
			}
		} catch (Exception e) {
			logger.error("{} Error service login: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		
		
		return responseRespuesta;
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public boolean recuperarClave(String cuo, int idUsuario) throws Exception {
		boolean rpta = false;

		return rpta;
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = true, rollbackFor = {Exception.class, SQLException.class})
	public ResponseUsuariosDTO obtenerUsuarios(String cuo, Map<String, Object> filters) throws Exception{
		 
		ResponseUsuariosDTO usuariosResponse = new ResponseUsuariosDTO();
		try {			
				usuariosResponse = usuarioDao.obtenerUsuarios(cuo, filters);
				if(usuariosResponse.getUsuarios() == null || usuariosResponse.getUsuarios().size() < 1) {
					usuariosResponse.setIndicador(ConstantesProject.RPTA_0);
					usuariosResponse.setMensaje("No se encontraron resultados");
					usuariosResponse.setUsuarios(null);
				} 
			return usuariosResponse;
		} catch (Exception e) {
			logger.error("{} Error service obtenerUsuarios: {}", cuo, e.getMessage());
			throw new Exception(e);
		}		
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public ResponseUsuariosDTO crearUsuario(String cuo, UsuarioDTO usuarioI) throws Exception {
		
		ResponseUsuariosDTO usuariosResponse = new ResponseUsuariosDTO();
		try {
			if(!usuarioDao.existeUsuario(cuo, usuarioI.getUsuario())) {
				usuarioDao.crearUsuario(cuo, usuarioI);
			} else {
				usuariosResponse.setIndicador(ConstantesProject.C_E03);
				usuariosResponse.setMensaje(ConstantesProject.X_E03 + ": " +usuarioI.getUsuario());
				usuariosResponse.setUsuarios(null);
			}	
			return usuariosResponse;
		} catch (Exception e) {
			logger.error("{} Error service crearUsuario: {}", cuo, e.getMessage());
			throw new Exception(e);
		}	
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public void actualizarUsuario(String cuo, UsuarioDTO usuarioU) throws Exception {
		usuarioDao.actualizarUsuario(cuo, usuarioU);
	}

}
