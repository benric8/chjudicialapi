package pe.gob.pj.hjudicial.api.app.apis;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.hjudicial.dao.dto.GlobalResponseDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestEntidadDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestUsuarioDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseEntidadesDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponsePerfilesDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseUsuariosDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.UsuarioDTO;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.service.MantenimientoService;
import pe.gob.pj.hjudicial.service.UsuarioService;

@RestController
public class MantenimientoApi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MantenimientoApi.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MantenimientoService mantenimientoService;	
	
	/* CRUD Usuarios */
	
	@GetMapping(value = "maestras/usuarios")
	public ResponseEntity<GlobalResponseDTO> obtenerUsuarios(@RequestAttribute String cuo,
															@RequestParam(required = false) Integer idUsuario,
															@RequestParam(required = false) String usuario){
		GlobalResponseDTO res = new GlobalResponseDTO();
		
		try {
		
			Map<String, Object> filtros = new LinkedHashMap<>();
			
			if (idUsuario != null)
			filtros.put(UsuarioDTO.P_ID_USUARIO, idUsuario);
			if (usuario != null)
			filtros.put(UsuarioDTO.P_USUARIO, usuario);

			ResponseUsuariosDTO usuariosResponse = usuarioService.obtenerUsuarios(cuo, filtros);
			res.setCodigo(String.format("%03d", Integer.parseInt(usuariosResponse.getIndicador())));
			res.setDescripcion(usuariosResponse.getMensaje());
			res.setData(usuariosResponse.getUsuarios());

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al obtener usuarios, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al obtener usurios, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@PostMapping(value="maestras/crear/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> crearUsuario(@RequestAttribute(name = ConstantesProject.AUD_CUO) String cuo,
			@Validated @RequestBody RequestUsuarioDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
				UsuarioDTO ud = new UsuarioDTO();
				BeanUtils.copyProperties(request, ud);
				ResponseUsuariosDTO response = usuarioService.crearUsuario(cuo, ud);
				if (UtilsProject.isNullOrEmpty(response.getMensaje())) {
					res.setCodigo(ConstantesProject.C_EXITO);
					res.setDescripcion(ConstantesProject.X_EXITO);
					res.setData(ud);
				} else {
					res.setCodigo(response.getIndicador());
					res.setDescripcion(response.getMensaje());
					res.setData(response.getUsuarios());					
				}

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al obtener usuarios, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al obtener usurios, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		} 
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@PutMapping(value="maestras/actualizar/usuario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> actualizarUsuario(@RequestAttribute(name = ConstantesProject.AUD_CUO) String cuo,
			@PathVariable(name = "id") Integer id,
			@Validated @RequestBody RequestUsuarioDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {			
				res.setCodigo(ConstantesProject.C_EXITO);
				res.setDescripcion(ConstantesProject.X_EXITO);
				
				UsuarioDTO ub = new UsuarioDTO();
				BeanUtils.copyProperties(request, ub);
				ub.setId(id);
				usuarioService.actualizarUsuario(cuo, ub);
				res.setData(ub);

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al obtener usuarios, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al obtener usurios, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		} 
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	/*CRUD MANTENIMIENTO*/
	
	@GetMapping(value = "maestras/entidades")
	public ResponseEntity<GlobalResponseDTO> obtenerEntidades(@RequestAttribute String cuo,
															@RequestParam(required = false) Integer idEntidad,
															@RequestParam(required = false) String entidad,
															@RequestParam(required = false) String activo){
		GlobalResponseDTO res = new GlobalResponseDTO();
		
		try {
		
			Map<String, Object> filtros = new LinkedHashMap<>();
			
			if (idEntidad != null)
			filtros.put(EntidadDTO.P_ID_ENTIDAD, idEntidad);
			if (entidad != null)
			filtros.put(EntidadDTO.P_ENTIDAD, entidad);
			if (activo != null)
			filtros.put(EntidadDTO.P_ACTIVO_ENTIDAD, activo);			

			ResponseEntidadesDTO entidadesResponse = mantenimientoService.obtenerEntidades(cuo, filtros);
			res.setCodigo(String.format("%03d", Integer.parseInt(entidadesResponse.getIndicador())));
			res.setDescripcion(entidadesResponse.getMensaje());
			res.setData(entidadesResponse.getEntidades());

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al obtener Entidades , Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al obtener Entidades , Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@PostMapping(value="maestras/crear/entidad", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> crearEntidad(@RequestAttribute(name = ConstantesProject.AUD_CUO) String cuo,
			@Validated @RequestBody RequestEntidadDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
				EntidadDTO ed = new EntidadDTO();
				BeanUtils.copyProperties(request, ed);
				mantenimientoService.crearEntidad(cuo, ed);
				
				res.setCodigo(ConstantesProject.C_EXITO);
				res.setDescripcion(ConstantesProject.X_EXITO);
				res.setData(ed);

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al crear entidad, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al crear entidad, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		} 
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@PutMapping(value="maestras/actualizar/entidad/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> actualizarEntidad(@RequestAttribute(name = ConstantesProject.AUD_CUO) String cuo,
			@PathVariable(name = "id") Integer id,
			@Validated @RequestBody RequestEntidadDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {			
				res.setCodigo(ConstantesProject.C_EXITO);
				res.setDescripcion(ConstantesProject.X_EXITO);
				
				EntidadDTO ed = new EntidadDTO();
				BeanUtils.copyProperties(request, ed);
				ed.setId(id);
				mantenimientoService.actualizarEntidad(cuo, ed);
				res.setData(ed);

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al actualizar entidad, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al actualizar entidad, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		} 
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	/* GET PERFILES */
	
	@GetMapping(value = "maestras/perfiles")
	public ResponseEntity<GlobalResponseDTO> obtenerPerfiles(@RequestAttribute String cuo){
		GlobalResponseDTO res = new GlobalResponseDTO();
		
		try {
		
			ResponsePerfilesDTO perfilesResponse = mantenimientoService.obtenerPerfiles(cuo);
			res.setCodigo(String.format("%03d", Integer.parseInt(perfilesResponse.getIndicador())));
			res.setDescripcion(perfilesResponse.getMensaje());
			res.setData(perfilesResponse.getPerfiles());

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion("");
			logger.error("{} Error al obtener perfiles, Descripción : {}", cuo , res.getDescripcion());
			logger.error("{} Error al obtener perfiles, Detalle => ERROR: {} | CAUSA {}", cuo , e.getMessage(), e.getCause());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
}
