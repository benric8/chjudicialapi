package pe.gob.pj.hjudicial.api.app.apis;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.hjudicial.dao.dto.GlobalResponseDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseValorGeneradoDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseTokenDTO;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.hjudicial.dao.utils.SecurityConstants;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;

/***
 * 
 * @author oruizb
 *
 */

@RestController
public class DefaultApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(DefaultApi.class);
	
	/**
	 * Método que sirve para verificar versión actual del aplicativo
	 * 
	 * @param cuo Código único de log
	 * @return Datos del aplicativo
	 */
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> verificarConexiones() {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {

			System.out.println("Resultado de función ultimoDía: " + ultimoDiaString(11,2023));

			res.setCodigo(ConstantesProject.C_200);
			res.setDescripcion("Versión actual de aplicativo");
			Map<String, String> healthcheck = new HashMap<String, String>();
			healthcheck.put("Aplicativo", "Historial Judicial API-REST");
			healthcheck.put("Estado", "Disponible");
			healthcheck.put("Versión", ConstantesProject.VERSION);
			res.setData(healthcheck);
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
			logger.error("Error al consultar versión de aplicativo hjudicialPersonal-api: {}", res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}

	public String ultimoDiaString(int mes, int anio) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(anio, mes - 1, 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		Date date = calendar.getTime();
		DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		return DATE_FORMAT.format(date);
	}

	@RequestMapping(value = "encriptar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseValorGeneradoDTO> generarClave(@RequestParam String usuario, @RequestParam String clave, @RequestAttribute String cuo) {
		ResponseValorGeneradoDTO d = new ResponseValorGeneradoDTO();
		try {
			d.setValorGenerado(EncryptUtils.encrypt(usuario, clave));
		} catch (Exception e) {
			logger.error("{} error al encriptar: {}", cuo , e.getMessage());
			logger.fatal(cuo, e);
		}
		return new ResponseEntity<ResponseValorGeneradoDTO>(d, HttpStatus.OK);
	}
	
	@RequestMapping(value = "encryptPastFrass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseValorGeneradoDTO> cifrarClave(@RequestParam String valor, @RequestAttribute String cuo) {
		ResponseValorGeneradoDTO d = new ResponseValorGeneradoDTO();
		try {
			d.setValorGenerado(EncryptUtils.encryptPastFrass(valor));
		} catch (Exception e) {
			logger.error("{} error al encryptPastFrass: {}", cuo , e.getMessage());
			logger.fatal(cuo, e);
		}
		return new ResponseEntity<ResponseValorGeneradoDTO>(d, HttpStatus.OK);
	}
	
	@RequestMapping(value = "decryptPastFrass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseValorGeneradoDTO> descriptarClave(@RequestParam String valor, @RequestAttribute String cuo) {
		ResponseValorGeneradoDTO d = new ResponseValorGeneradoDTO();
		try {
			d.setValorGenerado(EncryptUtils.decryptPastFrass(valor));
		} catch (Exception e) {
			logger.error("{} error al decryptPastFrass: {}", cuo , e.getMessage());
			logger.fatal(cuo, e);
		}
		return new ResponseEntity<ResponseValorGeneradoDTO>(d, HttpStatus.OK);
	}


	/**
	 * MÉTODO QUE GENERA NUEVO TOKEN A PARTIR DE TOKEN ANTERIOR
	 * 
	 * @param token           es token antentior
	 * @param ipRemota        es la ip desde donde lo solicita
	 * @param tokenAdmin      es el token de la seccion administrador
	 * @param validTokenAdmin indicador si necesitamos validar token del admin
	 * @param cuo             código único de log
	 * @return un nuevo token
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/seguridad/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseTokenDTO> refreshToken(@RequestParam String token, @RequestAttribute String cuo, @RequestAttribute String ipRemota, @RequestAttribute Date limit) {
		ResponseTokenDTO res = new ResponseTokenDTO();
		try {			
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
			res.setCodigo(ConstantesProject.C_500);
			
			try {
				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));
				String accesoBase =  (String) parsedToken.getBody().get(ConstantesProject.CLAIM_ACCESO);
				List<String> roles = (List<String>) parsedToken.getBody().get(ConstantesProject.CLAIM_ROL);
				String ipRemotaToken = parsedToken.getBody().get(ConstantesProject.CLAIM_IP).toString();
				Date limiteRefreshClaim = new Date(Long.parseLong(parsedToken.getBody().get(ConstantesProject.CLAIM_LIMIT).toString()));
				int total = (int) parsedToken.getBody().get(ConstantesProject.CLAIM_NUMERO);
				String subject = parsedToken.getBody().getSubject();
				
				Date ahora = new Date();
				Date limiteExpira = parsedToken.getBody().getExpiration();
				Date limiteRefresh = UtilsProject.sumarRestarSegundos(limiteExpira, ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS);
				
				if (ipRemota.equals(ipRemotaToken)) {
					if(limit.equals(limiteRefreshClaim)) {
						if(!ahora.after(limiteRefresh)) {
							String tokenResult = Jwts.builder()
									.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
									.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
									.setIssuer(SecurityConstants.TOKEN_ISSUER)
									.setAudience(SecurityConstants.TOKEN_AUDIENCE)
									.setSubject(subject).setExpiration(UtilsProject.sumarRestarSegundos(ahora, ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS))
									.claim(ConstantesProject.CLAIM_ROL, roles)
									.claim(ConstantesProject.CLAIM_IP, ipRemota)
									.claim(ConstantesProject.CLAIM_ACCESO, accesoBase)
									.claim(ConstantesProject.CLAIM_LIMIT, UtilsProject.sumarRestarSegundos(ahora, ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS + ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS))
									.claim(ConstantesProject.CLAIM_NUMERO, total + 1)
									.compact();
							res.setCodigo(ConstantesProject.C_200);
							res.setDescripcion("Token actualizado.");
							res.setToken(tokenResult);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}else {
							res.setCodigo(ConstantesProject.C_E01);
							res.setDescripcion(ConstantesProject.X_E01);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}
					}else {
						res.setCodigo(ConstantesProject.C_E02);
						res.setDescripcion(ConstantesProject.X_E02);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}					
				} else {
					res.setCodigo(ConstantesProject.C_401);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			} catch (ExpiredJwtException e) {
				String accesoBase =  (String) e.getClaims().get(ConstantesProject.CLAIM_ACCESO);
				List<String> roles = (List<String>) e.getClaims().get(ConstantesProject.CLAIM_ROL);
				String ipRemotaToken = e.getClaims().get(ConstantesProject.CLAIM_IP).toString();
				int total = (int) e.getClaims().get(ConstantesProject.CLAIM_NUMERO);
				String subject = e.getClaims().getSubject();
				Date limiteRefreshClaim = new Date(Long.parseLong(e.getClaims().get(ConstantesProject.CLAIM_LIMIT).toString()));

				Date ahora = new Date();
				Date limiteExpira = e.getClaims().getExpiration();
				Date limiteRefresh = UtilsProject.sumarRestarSegundos(limiteExpira, ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS);
				
				if (ipRemota.equals(ipRemotaToken)) {
					if(limit.equals(limiteRefreshClaim)) {
						if(!ahora.after(limiteRefresh)) {
							String tokenResult = Jwts.builder()
									.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
									.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
									.setIssuer(SecurityConstants.TOKEN_ISSUER)
									.setAudience(SecurityConstants.TOKEN_AUDIENCE)
									.setSubject(subject).setExpiration(UtilsProject.sumarRestarSegundos(ahora, ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS))
									.claim(ConstantesProject.CLAIM_ROL, roles)
									.claim(ConstantesProject.CLAIM_IP, ipRemota)
									.claim(ConstantesProject.CLAIM_ACCESO, accesoBase)
									.claim(ConstantesProject.CLAIM_LIMIT, UtilsProject.sumarRestarSegundos(ahora, ConstantesProject.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS + ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS))
									.claim(ConstantesProject.CLAIM_NUMERO, total + 1)
									.compact();
							res.setCodigo(ConstantesProject.C_200);
							res.setDescripcion("Token actualizado.");
							res.setToken(tokenResult);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}else {
							res.setCodigo(ConstantesProject.C_E01);
							res.setDescripcion(ConstantesProject.X_E01);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}
					}else {
						res.setCodigo(ConstantesProject.C_E02);
						res.setDescripcion(ConstantesProject.X_E02);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}
				} else {
					res.setCodigo(ConstantesProject.C_401);
					logger.warn(
							"{} No se ha encontrado coincidencias válidas del token anterior o se ha excedido el tiempo limite para refrescar token.",
							cuo);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			}
		} catch (Exception e) {
			logger.error("{} error al intentar generar nuevo Token: {}", cuo, UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
	}

}
