package pe.gob.pj.hjudicial.dao.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.hjudicial.dao.dto.ResponseLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseRespuestaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseUsuariosDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.OpcionDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaeEntidad;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaeOpcionPerfil;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaePerfil;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaeUsuario;
import pe.gob.pj.hjudicial.dao.repository.UsuarioDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;

/**
 * Objeto     : UsuarioDaoImpl.
 * Descripción: Clase que implementa las operaciones dao de usuario.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@Component("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UsuarioDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionHJudicial")
	private SessionFactory sf;

	@Override
	public ResponseLoginDTO login(String cuo, String usuario) throws Exception {
		
		ResponseLoginDTO loginResponse = new ResponseLoginDTO();
		UsuarioDTO usuarioDTO = null;

		try {
			
			this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
			.setParameter(MaeUsuario.P_USUARIO, usuario);
						
			this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ACTIVO)
			.setParameter(MaeUsuario.P_ACTIVO, ConstantesProject.ACTIVO_ESTADO);
			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_BY_FILTERS, MaeUsuario.class);
			MaeUsuario maeUsuario = query.getSingleResult();
			
			usuarioDTO = new UsuarioDTO();
			
			usuarioDTO.setId(maeUsuario.getNUsuario());
			usuarioDTO.setApellidosNombres(maeUsuario.getNombres().concat(!UtilsProject.isNullOrEmpty(maeUsuario.getApellidoPaterno()) ? " " + maeUsuario.getApellidoPaterno() : "").concat(!UtilsProject.isNullOrEmpty(maeUsuario.getApellidoMaterno()) ? " " + maeUsuario.getApellidoMaterno() : ""));
			usuarioDTO.setNumeroDocumento(maeUsuario.getNumeroDocumento());
			usuarioDTO.setActivo(maeUsuario.getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO) ? ConstantesProject.ACTIVO_DESCRICION : ConstantesProject.INACTIVO_DESCRIPCION);
			usuarioDTO.setUsuario(maeUsuario.getUsuario());
			usuarioDTO.setContrasenia(maeUsuario.getClave());
			usuarioDTO.setCorreo(maeUsuario.getCorreo());
			
			usuarioDTO.setEntidad(maeUsuario.getEntidad().getNombre());
			usuarioDTO.setPerfil(maeUsuario.getPerfil().getNombre());
			usuarioDTO.setCodigoRol(maeUsuario.getPerfil().getCodigoRol());
			
			this.sf.getCurrentSession().enableFilter(MaeOpcionPerfil.F_IDPERFIL)
			.setParameter(MaeOpcionPerfil.P_IDPERFIL, maeUsuario.getPerfil().getNPerfil());
			
			this.sf.getCurrentSession().enableFilter(MaeOpcionPerfil.F_ACTIVO)
			.setParameter(MaeOpcionPerfil.P_ACTIVO, ConstantesProject.ACTIVO_ESTADO);
			
			List<OpcionDTO> opcions = new ArrayList<OpcionDTO>();
			
			TypedQuery<MaeOpcionPerfil> queryOP = this.sf.getCurrentSession().createNamedQuery(MaeOpcionPerfil.NQ_FILTERS, MaeOpcionPerfil.class);
			queryOP.getResultList().forEach(item -> {
				opcions.add(new OpcionDTO(
						item.getOpcion().getNombre(), 
						item.getOpcion().getUrl(), 
						item.getOpcion().getIcono(), 
						item.getOpcion().getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO) ? ConstantesProject.ACTIVO_DESCRICION : ConstantesProject.INACTIVO_DESCRIPCION ));
			});
			
			usuarioDTO.setOpcions(opcions);

			loginResponse.setIndicador(ConstantesProject.RPTA_1);
			loginResponse.setMensaje("Acceso exitoso.");
			loginResponse.setUsuario(usuarioDTO);
		} catch (NoResultException not) {
			loginResponse.setIndicador(ConstantesProject.RPTA_0);
			loginResponse.setMensaje("El usuario no existe o está inactivo.");
		} catch (Exception e) {
			logger.error("{} Error dao login: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return loginResponse;
	}

	@Override
	public ResponseRespuestaDTO cambiarClave(String cuo, int idUsuario, String claveNueva) throws Exception {
		ResponseRespuestaDTO responseRespuesta = new ResponseRespuestaDTO();
		try {
			Query query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_UPDATE_CLAVE);
			query.setParameter(MaeUsuario.P_CLAVE, claveNueva);
			query.setParameter(MaeUsuario.P_ID, idUsuario);
			int count = query.executeUpdate();
			if(count > 0) {
				responseRespuesta.setIndicador(ConstantesProject.RPTA_1);
				responseRespuesta.setMensaje("Cambio de contraseña exitosa.");
			}
		} catch (Exception e) {
			logger.error("{} Error dao cambiarClave: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return responseRespuesta;
	}

	@Override
	public ResponseRespuestaDTO recuperarClave(String cuo, int idUsuario) throws Exception {
		return new ResponseRespuestaDTO(cuo, cuo);
	}

	@Override
	public ResponseUsuariosDTO obtenerUsuarios(String cuo, Map<String, Object> filters) throws Exception{
		ResponseUsuariosDTO rspt = new ResponseUsuariosDTO();
		try {
			if(!UtilsProject.isNullOrEmpty(filters.get(UsuarioDTO.P_ID_USUARIO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID)
					.setParameter(MaeUsuario.P_ID, filters.get(UsuarioDTO.P_ID_USUARIO));
			}
			if(!UtilsProject.isNullOrEmpty(filters.get(UsuarioDTO.P_USUARIO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
					.setParameter(MaeUsuario.P_USUARIO, filters.get(UsuarioDTO.P_USUARIO));
			}
			List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_BY_FILTERS, MaeUsuario.class);
			query.getResultStream().forEach(usuario->{
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO.setId(usuario.getNUsuario());
				//usuarioDTO.setApellidosNombres(usuario.getNombres().concat(!UtilsProject.isNullOrEmpty(usuario.getApellidoPaterno()) ? " " + usuario.getApellidoPaterno() : "").concat(!UtilsProject.isNullOrEmpty(usuario.getApellidoMaterno()) ? " " + usuario.getApellidoMaterno() : ""));
				usuarioDTO.setNombres(usuario.getNombres());
				usuarioDTO.setApellidoPaterno(usuario.getApellidoPaterno());
				usuarioDTO.setApellidoMaterno(usuario.getApellidoMaterno());
				usuarioDTO.setNumeroDocumento(usuario.getNumeroDocumento());
				//usuarioDTO.setActivo(usuario.getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO) ? ConstantesProject.ACTIVO_DESCRICION : ConstantesProject.INACTIVO_DESCRIPCION);
				usuarioDTO.setActivo(usuario.getActivo());
				usuarioDTO.setUsuario(usuario.getUsuario());
				usuarioDTO.setContrasenia(usuario.getClave());
				usuarioDTO.setCorreo(usuario.getCorreo());
				usuarioDTO.setEntidad(String.valueOf(usuario.getEntidad().getNEntidad()));
				usuarioDTO.setPerfil(String.valueOf(usuario.getPerfil().getNPerfil()));
				//usuarioDTO.setCodigoRol(usuario.getPerfil().getCodigoRol());
				lista.add(usuarioDTO);
			});
			
			rspt.setIndicador(ConstantesProject.RPTA_1);
			rspt.setMensaje("Operación exitosa.");
			rspt.setUsuarios(lista);
			
		}catch(Exception e) {
			logger.error("{} Error dao obtenerUsuarios: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return rspt;
	}
	
	@Override
	public void crearUsuario(String cuo, UsuarioDTO usuarioI) throws Exception {
		try {
			//Validar si existe usuario
			if(!UtilsProject.isNullOrEmpty(usuarioI.getId())) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID)
					.setParameter(MaeUsuario.P_ID, usuarioI.getId());	}
			if(!UtilsProject.isNullOrEmpty(usuarioI.getUsuario())) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
					.setParameter(MaeUsuario.P_USUARIO, usuarioI.getUsuario());	}
			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_BY_FILTERS, MaeUsuario.class);
			MaeUsuario usuario = query.getResultStream().findFirst().orElse(new MaeUsuario());
			
			if(UtilsProject.isNullOrEmpty(usuario.getNumeroDocumento())) {
				usuario.setUsuario(usuarioI.getUsuario());
				usuario.setClave(EncryptUtils.cryptBase64u(usuarioI.getNumeroDocumento(), Cipher.ENCRYPT_MODE));
				usuario.setNombres(usuarioI.getNombres());
				usuario.setNumeroDocumento(usuarioI.getNumeroDocumento());
				usuario.setApellidoPaterno(usuarioI.getApellidoPaterno());
				usuario.setApellidoMaterno(usuarioI.getApellidoMaterno());
				usuario.setCorreo(usuarioI.getCorreo());
				usuario.setActivo(ConstantesProject.ACTIVO_ESTADO);
				
				MaeEntidad entidad = new MaeEntidad();
				entidad.setNEntidad(Integer.valueOf(usuarioI.getEntidad()));
				usuario.setEntidad(entidad);
				
				MaePerfil perfil = new MaePerfil();
				perfil.setNPerfil(Integer.valueOf(usuarioI.getPerfil()));
				usuario.setPerfil(perfil);
				
				this.sf.getCurrentSession().save(usuario);
			}
		}catch(Exception e) {
			logger.error("{} Error dao crearUsuario: {}", cuo, e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public void actualizarUsuario(String cuo, UsuarioDTO usuarioU) throws Exception {
		//obtener datos actuales
		try {
		
		if(!UtilsProject.isNullOrEmpty(usuarioU.getId())) {
			this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID)
				.setParameter(MaeUsuario.P_ID, usuarioU.getId());	}
		TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_BY_FILTERS, MaeUsuario.class);
		MaeUsuario usuario = query.getSingleResult();
		if(!UtilsProject.isNullOrEmpty(usuario.getNUsuario())) {
			usuario.setUsuario(usuarioU.getUsuario());
			usuario.setNombres(usuarioU.getNombres());
			usuario.setApellidoPaterno(usuarioU.getApellidoPaterno());
			usuario.setApellidoMaterno(usuarioU.getApellidoMaterno());
			usuario.setActivo(usuarioU.getActivo());
			if(!UtilsProject.isNullOrEmpty(usuarioU.getContrasenia()) && usuarioU.getContrasenia().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO)) {
				usuario.setClave(EncryptUtils.cryptBase64u(usuarioU.getNumeroDocumento(), Cipher.ENCRYPT_MODE));
			}			
			MaeEntidad entidad = new MaeEntidad();
			entidad.setNEntidad(Integer.valueOf(usuarioU.getEntidad()));
			usuario.setEntidad(entidad);
			MaePerfil perfil = new MaePerfil();
			perfil.setNPerfil(Integer.valueOf(usuarioU.getPerfil()));
			usuario.setPerfil(perfil);
			usuario.setNumeroDocumento(usuarioU.getNumeroDocumento());
			usuario.setCorreo(usuarioU.getCorreo());
		} else {
			logger.error("{} Error dao actualizarEntidad, no se encontró usuario: {}", cuo);
		}
		this.sf.getCurrentSession().update(usuario);
		}catch(Exception e) {
			logger.error("{} Error dao actualizarUsuario: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean existeUsuario(String cuo, String usuario) throws Exception {
		if(!UtilsProject.isNullOrEmpty(usuario)) {
			this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
				.setParameter(MaeUsuario.P_USUARIO, usuario);	}
		TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.NQ_BY_FILTERS, MaeUsuario.class);
		return !query.getResultList().isEmpty();
	}

}
