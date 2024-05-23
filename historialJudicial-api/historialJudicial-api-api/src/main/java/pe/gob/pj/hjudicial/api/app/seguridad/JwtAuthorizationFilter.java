package pe.gob.pj.hjudicial.api.app.seguridad;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.SecurityConstants;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.service.SeguridadService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LogManager.getLogger(JwtAuthorizationFilter.class);

	@Getter
	@Setter
	private SeguridadService seguridadService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SeguridadService servicio) {
		super(authenticationManager);
		this.setSeguridadService(servicio);
	}

	/**
	* Descripción : filtra las peticiones HTTP y evalua el token
	* @param HttpServletRequest request - peticion HTTP
	* @param HttpServletResponse response, - respuesta HTTP    
	* @param FilterChain filterChain - cadenas filtro 
	* @return void 
	* @exception Captura excepcion generica
	*/
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		request.setAttribute(ConstantesProject.AUD_CUO, UtilsProject.obtenerCodigoUnico());
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			filterChain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	/**
	* Descripción : obtiene la autenticacion desde token
	* @param HttpServletRequest request - peticion HTTP
	* @return UsernamePasswordAuthenticationToken - Informacion de autenticacion proveniente token 
	* @exception Captura excepcion generica
	*/
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String remoteIp = request.getRemoteAddr();
		if(request.getRemoteAddr() == null) {
			remoteIp = request.getRemoteHost();
		}
		String urlReq = request.getRequestURI();
		String metodo = request.getMethod();
		if(metodo.equalsIgnoreCase(ConstantesProject.METHOD_CORTA_ULTIMA_BARRA_INVERTIDA)) {
			urlReq = urlReq.substring(0, urlReq.lastIndexOf("/"));//corta el id que se manda en la url
		}		
		String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
		String cuo = request.getAttribute(ConstantesProject.AUD_CUO).toString();
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			try {

				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));
				
				String accesoBase = (String) parsedToken.getBody().get(ConstantesProject.CLAIM_ACCESO);
				String username = parsedToken.getBody().getSubject();
				String usuario = (String) parsedToken.getBody().get(ConstantesProject.CLAIM_USUARIO);
				
				@SuppressWarnings("unchecked")
				List<String> roles = (List<String>) parsedToken.getBody().get(ConstantesProject.CLAIM_ROL);

				List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get(ConstantesProject.CLAIM_ROL)).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());
				
				String ipRemotaDeToken = parsedToken.getBody().get(ConstantesProject.CLAIM_IP).toString();
				Date limiteRefreshClaim = new Date(Long.parseLong(parsedToken.getBody().get(ConstantesProject.CLAIM_LIMIT).toString()));
								
				Date ahora = new Date();
				Date limiteExpira = parsedToken.getBody().getExpiration();
				Date limiteRefresh = UtilsProject.sumarRestarSegundos(limiteExpira, ConstantesProject.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS);

				if (!urlReq.endsWith("refresh")) {
					boolean tieneAcceso = seguridadService.validarAccesoMetodo(username, roles.get(0), urlReq, cuo);
					if (!tieneAcceso) {
						log.info("{} El usuario [{}], no tiene acceso al método [{}] ", cuo, username, urlReq);
						return null;
					}
				}

				if (!remoteIp.equals(ipRemotaDeToken) || ahora.after(limiteRefresh)) {
                	logger.error(cuo+" hubo problema con la ip remoto o limite de expiración: "+token);
					return null;
				}

				if(!urlReq.endsWith("refresh") && ((!urlReq.endsWith("login") 
						&& !accesoBase.equalsIgnoreCase(ConstantesProject.TOKEN_ACCESO_INTERNO)) || (urlReq.endsWith("login") && !accesoBase.equalsIgnoreCase(ConstantesProject.TOKEN_ACCESO_EXTERNO)))) {
					log.info("{} El token no tiene permiso [{}] para el método [{}]. ", cuo, ConstantesProject.TOKEN_ACCESO_INTERNO, urlReq);
					return null;
				}

				if (StringUtils.isNotEmpty(username)) {
					request.setAttribute(ConstantesProject.AUD_CUO, cuo);
					if(urlReq.endsWith("refresh") || urlReq.endsWith("login")) {
						request.setAttribute(ConstantesProject.REMOTE_IP, remoteIp);
						request.setAttribute(ConstantesProject.CLAIM_LIMIT, limiteRefreshClaim);
					} else if (urlReq.endsWith("buscarMarcacion")) {
						request.setAttribute("usuario", usuario);
					}
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				}
				
			} catch (ExpiredJwtException exception) {
				String ipRemotaToken = exception.getClaims().get(ConstantesProject.CLAIM_IP).toString();
				String subject = exception.getClaims().getSubject();
				int total = (int) exception.getClaims().get(ConstantesProject.CLAIM_NUMERO);			
				
//				Date ahora = new Date();				
				Date limiteRefreshClaim = new Date(Long.parseLong(exception.getClaims().get(ConstantesProject.CLAIM_LIMIT).toString()));
				
				if (urlReq.endsWith("refresh") && remoteIp.equals(ipRemotaToken) && total<=ConstantesProject.NRO_VECES_REFRESH_CON_TOKEN_EXPIRADO) { // && !ahora.after(limiteRefresh)
					List<SimpleGrantedAuthority> authorities = ((List<?>) exception.getClaims().get(ConstantesProject.CLAIM_ROL)).stream()
							.map(authority -> new SimpleGrantedAuthority((String) authority))
							.collect(Collectors.toList());
					++total;
					request.setAttribute(ConstantesProject.AUD_CUO, cuo);
					request.setAttribute(ConstantesProject.REMOTE_IP, remoteIp);
					request.setAttribute(ConstantesProject.CLAIM_LIMIT, limiteRefreshClaim);
					return new UsernamePasswordAuthenticationToken(subject, null, authorities);
				}
				log.warn(cuo + "Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn(cuo + "Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn(cuo + "Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (SignatureException exception) {
				log.warn(cuo + "Request to parse JWT with invalid signature : {} failed : {}", token,
						exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn(cuo + "Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			} catch (Exception e) {
				log.error(cuo + "No se obtubo owner y código de BASE DE DATOS: "
						+ UtilsProject.convertExceptionToString(e));
				e.printStackTrace();
			}
		}
		log.error(cuo + "Hubo un problema con el token : " + token);
		return null;
	}

}