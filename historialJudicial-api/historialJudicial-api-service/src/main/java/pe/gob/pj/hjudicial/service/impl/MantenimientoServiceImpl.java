package pe.gob.pj.hjudicial.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.hjudicial.dao.dto.ResponseEntidadesDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponsePerfilesDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;
import pe.gob.pj.hjudicial.dao.repository.MantenimientoDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.service.MantenimientoService;

@Service("mantenimientoService")
public class MantenimientoServiceImpl implements MantenimientoService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MantenimientoServiceImpl.class);

	@Autowired
	private MantenimientoDao mantenimientoDao;
	
	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = true, rollbackFor = {Exception.class, SQLException.class})	
	public ResponseEntidadesDTO obtenerEntidades(String cuo, Map<String, Object> filters) throws Exception {
		 
		ResponseEntidadesDTO entidadesResponse;
		try {

			entidadesResponse = mantenimientoDao.obtenerEntidades(cuo, filters);
			
			if(entidadesResponse.getEntidades() == null || entidadesResponse.getEntidades().size() < 1) {
				entidadesResponse.setIndicador(ConstantesProject.RPTA_0);
				entidadesResponse.setMensaje("No se encontraron resultados");
				entidadesResponse.setEntidades(null);
			} 
			return entidadesResponse;
			
		} catch (Exception e) {
			logger.error("{} Error service obtenerEntidades: {}", cuo, e.getMessage());
			throw new Exception(e);
		}		
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public void crearEntidad(String cuo, EntidadDTO entidadI) throws Exception {
		mantenimientoDao.crearEntidad(cuo, entidadI);
	}

	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {Exception.class, SQLException.class})
	public void actualizarEntidad(String cuo, EntidadDTO entidadU) throws Exception {
		mantenimientoDao.actualizarEntidad(cuo, entidadU);
	}

	
	@Override
	@Transactional(transactionManager = "txManagerHJudicial", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = true, rollbackFor = {Exception.class, SQLException.class})	
	public ResponsePerfilesDTO obtenerPerfiles(String cuo) throws Exception {
		 
		ResponsePerfilesDTO perfilesResponse;
		try {

			perfilesResponse = mantenimientoDao.obtenerPerfiles(cuo);
			
			if(perfilesResponse.getPerfiles() == null || perfilesResponse.getPerfiles().size() < 1) {
				perfilesResponse.setIndicador(ConstantesProject.RPTA_0);
				perfilesResponse.setMensaje("No se encontraron resultados");
				perfilesResponse.setPerfiles(null);
			} 
			return perfilesResponse;
			
		} catch (Exception e) {
			logger.error("{} Error service obtenerEntidades: {}", cuo, e.getMessage());
			throw new Exception(e);
		}		
	}
}
