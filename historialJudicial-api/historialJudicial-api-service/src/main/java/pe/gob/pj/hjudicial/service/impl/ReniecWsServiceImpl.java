/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

import pe.gob.pj.hjudicial.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject.Reniec;
import pe.gob.pj.hjudicial.restclient.reniec.Auditoria;
import pe.gob.pj.hjudicial.restclient.reniec.PersonaConsultada;
import pe.gob.pj.hjudicial.restclient.reniec.PersonaDTO;
import pe.gob.pj.hjudicial.restclient.reniec.ReniecResponse;
import pe.gob.pj.hjudicial.restclient.reniec.RequestConsultarPersona;
import pe.gob.pj.hjudicial.restclient.reniec.TokenAuthResponse;
import pe.gob.pj.hjudicial.service.ReniecWsService;

/**
 * <pre>
 * Objeto     : ReniecWsServiceImpl.
 * Descripción: Interface que implementa los métodos que permiten intaractuar con el servicio de RENIEC..
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Service("reniecWsService")
public class ReniecWsServiceImpl implements ReniecWsService {
	

	/**
	 * {@inheritDoc}
	 */
	/*
	 * @Override public ConsultaReniecResponse consultaReniec(ConsultaReniecPortType
	 * port, ConsultaReniec consultaReniecRequest) throws Exception {
	 * ConsultaReniecResponse response = new ConsultaReniecResponse();
	 * 
	 * try{ Holder<String> resTrama = new Holder <String>() ; Holder<String>
	 * resCodigo = new Holder <String>() ; Holder<String> resDescripcion = new
	 * Holder <String>(); Holder<String> resTotalRegistros = new Holder <String>();
	 * Holder<String> resPersona= new Holder <String>(); Holder<byte[]> resFoto =
	 * new Holder <byte[]>(); Holder<byte[]> resFirma = new Holder <byte[]>();
	 * Holder<String> resListaPersonas = new Holder <String>();
	 * 
	 * port.consultaReniec(consultaReniecRequest.getReqTrama(),
	 * consultaReniecRequest.getReqDniConsultante(),
	 * consultaReniecRequest.getReqTipoConsulta(),
	 * consultaReniecRequest.getReqUsuario(), consultaReniecRequest.getReqIp(),
	 * consultaReniecRequest.getReqDni(), consultaReniecRequest.getReqNombres(),
	 * consultaReniecRequest.getReqApellidoPaterno(),
	 * consultaReniecRequest.getReqApellidoMaterno(),
	 * consultaReniecRequest.getReqNroRegistros(),
	 * consultaReniecRequest.getReqGrupo(),
	 * consultaReniecRequest.getReqDniApoderado(),
	 * consultaReniecRequest.getReqTipoVinculoApoderado(), resTrama, resCodigo,
	 * resDescripcion, resTotalRegistros, resPersona, resFoto, resFirma,
	 * resListaPersonas);
	 * 
	 * response.setResCodigo(resCodigo.value);
	 * response.setResDescripcion(resDescripcion.value);
	 * response.setResFirma(resFirma.value); response.setResFoto(resFoto.value);
	 * response.setResListaPersonas(resListaPersonas.value);
	 * response.setResPersona(resPersona.value);
	 * response.setResTotalRegistros(resTotalRegistros.value);
	 * response.setResTrama(resTrama.value); return response; }catch(Exception e){
	 * log.error("Ha ocurrido un error invocando a RENIEC: {}", e.getMessage());
	 * throw e; } }
	 */
	
	@Autowired
    private RestTemplate restTemplate;
	

	/** Reniec **/
	private String ENDPOINT_SERVICIO_RENIEC = ConfiguracionPropiedades.getInstance().getProperty(Reniec.ENDPOINT);

	//private String TIPO_CONSULTA_SERVICIO_RENIEC_POR_DATOS = "1";
	//private String TIPO_CONSULTA_SERVICIO_RENIEC_POR_DNI = "2";
	private final String USERNAME = ConfiguracionPropiedades.getInstance().getProperty(Reniec.USUARIO_RENIEC);
	private final String PASSWORD = ConfiguracionPropiedades.getInstance().getProperty(Reniec.PASSWORD_RENIEC);
	private final String CODIGO_CLIENTE = ConfiguracionPropiedades.getInstance().getProperty(Reniec.CLIENTE_RENIEC);
	private final String CODIGO_ROL = ConfiguracionPropiedades.getInstance().getProperty(Reniec.ROL_RENIEC);
	private final String CODIGO_APLICATIVO = ConfiguracionPropiedades.getInstance().getProperty(Reniec.CODIGO_APLICATIVO_RENIEC);
	private final String MOTIVO = ConfiguracionPropiedades.getInstance().getProperty(Reniec.MOTIVO_RENIEC);

	RequestConsultarPersona requestConsultarPersona = new RequestConsultarPersona();
	PersonaConsultada personaConsultada = new PersonaConsultada();
	

	public Auditoria getDataAuditoria(String pcName, String ipAddress, String macAddress, String usuario) throws Exception {
		Auditoria auditoria = new  Auditoria();
		
		
		auditoria.setUsuario(usuario);
		auditoria.setNombrePc(pcName);
		auditoria.setNumeroIp(ipAddress);
		auditoria.setDireccionMac(macAddress);

		return auditoria;
	}
	
	public TokenAuthResponse getTokenAuthentication() throws Exception {
		log.info("INICIO: OBTENER TOKEN RENIEC");
		TokenAuthResponse response = null;
		String URIAuthentication = ENDPOINT_SERVICIO_RENIEC + "/api/authenticate";
		HttpHeaders headers = new HttpHeaders();
		headers.add("username",USERNAME);
		headers.add("password",PASSWORD);
		headers.add("codigoCliente",CODIGO_CLIENTE);
		headers.add("codigoRol",CODIGO_ROL);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>  (headers);
		UriComponentsBuilder builderUrl = UriComponentsBuilder.fromUriString(URIAuthentication);
		
		try {
			ResponseEntity<TokenAuthResponse> responseEntity = restTemplate.postForEntity(builderUrl.buildAndExpand().toUri(),entity, TokenAuthResponse.class);
			if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
				response = responseEntity.getBody();
			} else {
				throw new Error("Ocurrio un error al obtener token de authentication"+responseEntity.getStatusCodeValue());
			}
		}catch(Exception e) {
			throw e;
		}
		log.info("FIN: OBTENER TOKEN RENIEC");
		return response;
	}
	
	public PersonaDTO obtenerPersonaPorDNI(String dni, String pcName, String ipAddress, String macAddress, String usuario) throws Exception {
		PersonaDTO persona = null;
		
		log.info("INICIO: OBTENER DATOS RENIEC");
		String URIConsultaReniec = ENDPOINT_SERVICIO_RENIEC+"/reniec/buscar/persona-dni";
		
		requestConsultarPersona.setFormatoRespuesta("json");
		requestConsultarPersona.setNumeroDocumentoIdentidad(dni);
		requestConsultarPersona.setMotivo(MOTIVO);
		requestConsultarPersona.setCodigoAplicativo(CODIGO_APLICATIVO);
		requestConsultarPersona.setAuditoria(getDataAuditoria(pcName,ipAddress,macAddress,usuario));
		
		try {
			
			TokenAuthResponse token = getTokenAuthentication();
						
	        // Configurar los encabezados
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", "Bearer " + token.getToken());
	        
	        UriComponentsBuilder builderUrl = UriComponentsBuilder.fromUriString(URIConsultaReniec);
	        // Crear la entidad de la solicitud HTTP con el cuerpo y los encabezados
	        HttpEntity<RequestConsultarPersona> requestEntity = new HttpEntity<>(requestConsultarPersona, headers);

	        // Realizar la solicitud HTTP POST al API
	        ResponseEntity<ReniecResponse> responseEntity =  restTemplate.exchange(builderUrl.buildAndExpand().toUri(), HttpMethod.POST, requestEntity, ReniecResponse.class);
	        if(responseEntity.getBody().getCodigo().equals("0000")) {
	        	persona = responseEntity.getBody().getData();	        	
	        }else {
	        	throw new Error("Ocurrio un error al consultar el servicio de RENIEC");
	        }
	        
		} catch (Exception e) {
		  throw e;
		}
		log.info("FIN: OBTENER DATOS RENIEC");
		return persona;
	}
}
