package pe.gob.pj.hjudicial.api.app.apis;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniec;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecPortType;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecResponse;
import pe.gob.pj.hjudicial.bean.EntidadPideBean;
import pe.gob.pj.hjudicial.dao.dto.GlobalResponseDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestCambiarClaveDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestConsultaPrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestReniecDTO;
import pe.gob.pj.hjudicial.dao.dto.RequestSunatDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaPrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseReniecDTO;
import pe.gob.pj.hjudicial.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.ReniecUtils;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.service.ConsultaCondenasService;
import pe.gob.pj.hjudicial.service.ConsultaPrisionPreventivaService;
import pe.gob.pj.hjudicial.service.ReniecWsService;
import pe.gob.pj.hjudicial.service.SunatWsService;

/**
 * Objeto     : ConsultaApi.
 * Descripción: Clase que implementa los endpoint relacionados a las búsquedas de prisiones preventivas y condenas.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@RestController
public class ConsultaApi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ConsultaApi.class);
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private ConsultaCondenasService consulta;
	
	@Autowired
	private ConsultaPrisionPreventivaService consultaPrisionPreventivaService;
	
	@Autowired
	private ReniecWsService reniecWsService;
	
	@Autowired
	private SunatWsService sunatWsService;
	
	@RequestMapping(value = "/consultaCondenas",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> consultarCondenas(@RequestAttribute String cuo, @RequestBody RequestConsultaCondenasDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			if(UtilsProject.isNullOrEmpty(request.getTipoConsulta()) || (!request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_CONDENAS_DNI) && !request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_CONDENAS_NOMBRES))) {
				res.setCodigo(ConstantesProject.C_400);
				res.setDescripcion("tipoConsulta("+ request.getTipoConsulta() +"): El tipo de búsqueda tiene un valor no valido [D=DNI,N=NOMBRES].");
			}else {
				if(request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_CONDENAS_DNI) && UtilsProject.isNullOrEmpty(request.getDocumento())) {
					res.setCodigo(ConstantesProject.C_400);
					res.setDescripcion("documento("+ request.getDocumento() +"): El número de documento tiene un valor no permitido.");
				}else if(request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_CONDENAS_NOMBRES) && (UtilsProject.isNullOrEmpty(request.getApellidoPaterno()) && UtilsProject.isNullOrEmpty(request.getNombres()))) {
					res.setCodigo(ConstantesProject.C_400);
					res.setDescripcion("apellidoPaterno("+ request.getApellidoPaterno() +"), nombres("+ request.getNombres() +"): El apellidoPaterno y/o nombres tiene un valor no permitido.");
				}else {
					ResponseConsultaCondenasDTO responseCondenas = consulta.consultaCondenas(cuo, request);
					res.setCodigo(String.format("%03d", Integer.parseInt(responseCondenas.getIndicador())));
					res.setDescripcion(responseCondenas.getMensaje());
					res.setData(responseCondenas.getCondenas());
				}
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error en la consulta de condenas por documento.");			
			logger.error("{} Error api consultaCondenas: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultaPrisionPreventiva",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> consultarPrisionPreventiva(@RequestAttribute String cuo, @RequestBody RequestConsultaPrisionPreventivaDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			if(UtilsProject.isNullOrEmpty(request.getTipoConsulta()) || (!request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_DNI) && !request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_NOMBRES))) {
				res.setCodigo(ConstantesProject.C_400);
				res.setDescripcion("tipoConsulta("+ request.getTipoConsulta() +"): El tipo de búsqueda tiene un valor no valido [D=DNI,N=NOMBRES].");
			}else {
				if(request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_DNI) && UtilsProject.isNullOrEmpty(request.getDocumento())) {
					res.setCodigo(ConstantesProject.C_400);
					res.setDescripcion("documento("+ request.getDocumento() +"): El número de documento tiene un valor no permitido.");
				}else if(request.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_NOMBRES) && (UtilsProject.isNullOrEmpty(request.getApellidoPaterno()) && UtilsProject.isNullOrEmpty(request.getNombres()))) {
					res.setCodigo(ConstantesProject.C_400);
					res.setDescripcion("apellidoPaterno("+ request.getApellidoPaterno() +"), nombres("+ request.getNombres() +"): El apellidoPaterno y/o nombres tiene un valor no permitido.");
				}else {
					ResponseConsultaPrisionPreventivaDTO resPP = consultaPrisionPreventivaService.consultaPrisionesPreventivas(cuo, request, httpServletRequest.getRemoteAddr());
					res.setCodigo(String.format("%03d", Integer.parseInt(resPP.getIndicador())));
					res.setDescripcion(resPP.getMensaje());
					res.setData(resPP.getPrisionesPreventivas());
				}
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error en la consulta de prisiones preventivas.");			
			logger.error("{} Error api consultaPrisionPreventiva: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultaReniec",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> consultarPersonaReniecPorDni(@RequestAttribute String cuo, @RequestBody RequestReniecDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			if (null == request || null == request.getDni() || request.getDni().isEmpty()) {
				res.setCodigo(ConstantesProject.C_400);
				res.setDescripcion("El DNI no es válido.");
			} else {
				String endpoint = ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Reniec.ENDPOINT);
				String timeout = ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Reniec.TIMEOUT);
				String dniConsulta = ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Reniec.DNI_CONSULTA);
				ConsultaReniec consultaReniecRequest = new ConsultaReniec();
				consultaReniecRequest.setReqDni(request.getDni());
				consultaReniecRequest.setReqDniConsultante(dniConsulta);
				consultaReniecRequest.setReqTipoConsulta(ConstantesProject.Reniec.TIPO_CONSULTA_POR_NUMERO_DNI);
				consultaReniecRequest.setReqUsuario(ConstantesProject.USUARIO_CONSULTA_DEFAULT);
				consultaReniecRequest.setReqIp(httpServletRequest.getRemoteAddr());
				
				ConsultaReniecPortType portType = ReniecUtils.getPortReniec(endpoint, Integer.parseInt(timeout));
				ConsultaReniecResponse responseReniec = reniecWsService.consultaReniec(portType, consultaReniecRequest);
				String[] arrayPersona = responseReniec.getResPersona().split("\t");
				
				if (null != arrayPersona && arrayPersona.length < 2) {
					res.setCodigo(ConstantesProject.RPTA_1);
					res.setDescripcion("No se encontraron resultados.");
					res.setData(null);
				} else {
					ResponseReniecDTO resReniec = new ResponseReniecDTO();
					resReniec.setApellidoPaterno(arrayPersona[2]);
					resReniec.setApellidoMaterno(arrayPersona[3]);
					resReniec.setNombres(arrayPersona[5]);
					
					res.setCodigo(ConstantesProject.RPTA_1);
					res.setDescripcion("La consulta a RENIEC se realizó con éxito.");
					res.setData(resReniec);
				}
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error en la consulta a RENIEC.");			
			logger.error("{} Error api consultaReniec: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/consultaSunat",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> consultarPorRUC(@RequestAttribute String cuo, @RequestBody RequestSunatDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			
			if(request != null && request.getNumeroRuc() != null && !request.getNumeroRuc().isEmpty()) {
				EntidadPideBean entidad = sunatWsService.consultarSunatxRuc(request.getNumeroRuc());
				if(entidad != null) {
					res.setCodigo(ConstantesProject.RPTA_1);
					res.setDescripcion("La consulta a SUNAT se realizó con éxito.");
					res.setData(entidad);
				}
			} else {
				res.setCodigo(ConstantesProject.C_400);
				res.setDescripcion("El RUC no es válido.");
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesProject.C_500);
			res.setDescripcion(cuo + " : Ocurrio un error en la consulta a SUNAT.");			
			logger.error("{} Error api consultarSunatxRuc: {}", cuo , UtilsProject.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}	
	
}
