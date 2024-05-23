package pe.gob.pj.hjudicial.api.app.apis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.hjudicial.dao.dto.GlobalResponseDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestCambiarClaveDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseLoginDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseRespuestaDTO;
import pe.gob.pj.hjudicial.dao.utils.CaptchaUtils;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.SecurityConstants;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.service.UsuarioService;

/**
 * Objeto     : UsuarioApi.
 * Descripción: Clase que implementa los endpoint relacionados a usuarios.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@RestController
public class UsuarioApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UsuarioApi.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> ingresoSistema(@RequestAttribute String cuo, @RequestAttribute String ipRemota, @RequestAttribute Date limit, @Validated @RequestBody RequestLoginDTO login) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
//			logger.info("{} Inicio de método {}",cuo,"login");	
//			logger.info("{} Usuario {}",cuo,login.getUsuario());
			if(!UtilsProject.isNull(login.getAplicaCaptcha()).equals(ConstantesProject.LETRA_S) || (UtilsProject.isNull(login.getAplicaCaptcha()).equals(ConstantesProject.LETRA_S) && !UtilsProject.isNullOrEmpty(login.getTokenCaptcha()))) {	
				if(!UtilsProject.isNull(login.getAplicaCaptcha()).equals(ConstantesProject.LETRA_S) || CaptchaUtils.validCaptcha(login.getTokenCaptcha(), ipRemota, cuo)) {
					ResponseLoginDTO loginResponse = usuarioService.login(cuo, login.getUsuario(), login.getContrasenia());
					res.setCodigo(String.format("%03d", Integer.parseInt(loginResponse.getIndicador())));
					res.setDescripcion(loginResponse.getMensaje());
					if(loginResponse != null && loginResponse.getUsuario()!=null){
						String usuarioCompleto = login.getUsuario();
						usuarioCompleto = usuarioCompleto + "-" + loginResponse.getUsuario().getApellidosNombres();
						String token  = getNewToken(login.getToken(), usuarioCompleto, loginResponse.getUsuario().getCodigoRol(), ipRemota, cuo, limit);
						loginResponse.getUsuario().setCodigoRol("*********");
						if(UtilsProject.isNull(token).length() > 0) {
							loginResponse.getUsuario().setToken(token);
							res.setData(loginResponse.getUsuario());
						} else {
							res.setCodigo(ConstantesProject.C_E02);
							res.setDescripcion(ConstantesProject.X_E02);
						}
					}
				}else {
					res.setCodigo(ConstantesProject.C_404);
					res.setDescripcion("No se ha podido validar código captcha, por favor volver a intentarlo.");			
//					logger.error("{} Error al autenticar usuario: {}", cuo , "No se ha podido validar código captcha, por favor volver a intentarlo.");	
				}
			}else {
				res.setCodigo(ConstantesProject.C_400);
				res.setDescripcion("tokenCaptcha("+ login.getTokenCaptcha() + "): El token captcha no puede ser nulo o vacio.");			
//				logger.error("{} Error al autenticar usuario: {}", cuo , "No se ha podido validar código captcha, por favor volver a intentarlo.");	
			}

		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error en la autenticación del usuario.");			
			logger.error("{} Error api login: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
//			e.printStackTrace();
		}
//		logger.info("{} Fin de método {}",cuo,"login");	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cambiarClave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> cambiarClave(@RequestAttribute String cuo, @Validated @RequestBody RequestCambiarClaveDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			if(!request.getClaveActual().equalsIgnoreCase(request.getClaveNueva())) {
				ResponseRespuestaDTO respuestaResponse = usuarioService.cambiarClave(cuo, request.getUsuario(), request.getClaveActual(), request.getClaveNueva());
				res.setCodigo(String.format("%03d", Integer.parseInt(respuestaResponse.getIndicador())));
				res.setDescripcion(respuestaResponse.getMensaje());
			}else {
				res.setCodigo(String.format("%03d", Integer.parseInt(ConstantesProject.RPTA_0)));
				res.setDescripcion("La clave actual con la clave nueva no pueden ser las mismas.");
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error al cambiar la clave.");		
			logger.error("{} Error al autenticar usuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	public String getNewToken(String token, String usuario, String rol, String ipRemota,  String cuo, Date limit){
		String newToken = "";
		try {
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();		
			try {				
				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));
				List<String> roles = new ArrayList<String>();
				roles.add(rol);
//				@SuppressWarnings("unchecked")
//				List<String> roles = (List<String>) parsedToken.getBody().get(ConstantesProject.CLAIM_ROL);
				String ipRemotaToken = parsedToken.getBody().get(ConstantesProject.CLAIM_IP).toString();
				Date limiteRefreshClaim = new Date(Long.parseLong(parsedToken.getBody().get(ConstantesProject.CLAIM_LIMIT).toString()));
				int total = (int) parsedToken.getBody().get(ConstantesProject.CLAIM_NUMERO);
				String subject = parsedToken.getBody().getSubject();
				
				Integer tiempoToken = ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS * 1000 ;
				Date ahora = new Date();
				if(limit.equals(limiteRefreshClaim) && ipRemota.equals(ipRemotaToken)) {
					newToken = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + tiempoToken))
							.claim(ConstantesProject.CLAIM_ROL, roles)
							.claim(ConstantesProject.CLAIM_USUARIO, usuario)
							.claim(ConstantesProject.CLAIM_IP, ipRemota)
							.claim(ConstantesProject.CLAIM_ACCESO, ConstantesProject.TOKEN_ACCESO_INTERNO)
							.claim(ConstantesProject.CLAIM_LIMIT, UtilsProject.sumarRestarSegundos(ahora, ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS + ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS))
							.claim("numero", total + 1)
							.compact();
				} 
			} catch (ExpiredJwtException e) {
				List<String> roles = new ArrayList<String>();
				roles.add(rol);
				String ipRemotaToken = e.getClaims().get("remoteIp").toString();
				Date limiteRefreshClaim = new Date(Long.parseLong(e.getClaims().get(ConstantesProject.CLAIM_LIMIT).toString()));
				int total = (int) e.getClaims().get("numero");
				String subject = e.getClaims().getSubject();				
				Integer tiempoToken = 900000;
				if(limit.equals(limiteRefreshClaim) && ipRemota.equals(ipRemotaToken)) {
					newToken = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + tiempoToken))
							.claim(ConstantesProject.CLAIM_ROL, roles)
							.claim(ConstantesProject.CLAIM_USUARIO, usuario)
							.claim(ConstantesProject.CLAIM_IP, ipRemota)
							.claim(ConstantesProject.CLAIM_NUMERO, total + 1)
							.compact();
				} 
			}			
		} catch (Exception e) {
			logger.error("{} error al intentar generar nuevo Token: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return newToken;
	}

	
}
