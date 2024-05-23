package pe.gob.pj.hjudicial.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.hjudicial.dao.dto.RequestConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.repository.ConsultaCondenasDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.service.ConsultaCondenasService;

/**
 * Objeto     : ConsultaCondenasServiceImpl.
 * Descripción: Clase que implementa la busqueda de condenas.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@Service("consultaCondenasService")
public class ConsultaCondenasServiceImpl implements Serializable, ConsultaCondenasService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ConsultaCondenasServiceImpl.class);
	
	@Autowired
	private ConsultaCondenasDao consulta;

	@Override
	@Transactional(transactionManager = "txManagerCondenas", propagation = Propagation.REQUIRES_NEW, timeout = 200, readOnly = true, noRollbackFor = {Exception.class, SQLException.class})
	public ResponseConsultaCondenasDTO consultaCondenas(String cuo, RequestConsultaCondenasDTO filtro) throws Exception {
		ResponseConsultaCondenasDTO consultaResponse = null;
		try {
			if(filtro.getTipoConsulta().equals(ConstantesProject.TIPO_BUSQUEDA_CONDENAS_DNI)) {
				consultaResponse = consulta.consultaXDni(cuo, filtro.getDocumento());
			} else {
				consultaResponse = consulta.consultaXNombres(cuo, filtro.getApellidoPaterno(), filtro.getApellidoMaterno(), filtro.getNombres());
			}
		} catch (Exception e) {
			logger.error("{} Error service consultaCondenas: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return consultaResponse;
	}
	
}
