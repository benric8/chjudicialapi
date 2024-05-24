/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.hjudicial.dao.dto.RequestConsultaPrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaPrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.repository.ConsultaPrisionPreventivaDao;
import pe.gob.pj.hjudicial.dao.repository.callable.ConsultaPrisionPreventivaPorApellidosNombresCallable;
import pe.gob.pj.hjudicial.dao.repository.callable.ConsultaPrisionPreventivaPorDniCallable;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.service.ConsultaPrisionPreventivaService;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;
import pe.gob.pj.pjseguridad.dao.repository.PjSeguridadDao;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaServiceImpl.
 * Descripción: Clase que implementa las operaciones de consulta de prisiones preventivas.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Service("consultaPrisionPreventivaService")
public class ConsultaPrisionPreventivaServiceImpl implements ConsultaPrisionPreventivaService {
	
	@Autowired
	private ConsultaPrisionPreventivaDao consultaPrisionPreventivaDao;
	
	@Autowired
	private PjSeguridadDao pjSeguridadDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseConsultaPrisionPreventivaDTO consultaPrisionesPreventivas(String cuo, 
			RequestConsultaPrisionPreventivaDTO request, String ipRemoto) throws Exception {
		ResponseConsultaPrisionPreventivaDTO response;
		if (ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_NOMBRES.equals(request.getTipoConsulta()) && null == request.getApellidoMaterno()) {
			request.setApellidoMaterno("%");
		}
		List<DataSourceDTO> lstDataSource = pjSeguridadDao.obtenerDataSources();
		if (null == lstDataSource || lstDataSource.isEmpty()) {
			String mensaje = "No existe la lista de data sources para el proyecto HistorialJudicial";
			log.warn(mensaje);
			response = new ResponseConsultaPrisionPreventivaDTO(ConstantesProject.RPTA_0, mensaje, null);
			return response;
		}
		
		List<PrisionPreventivaDTO> lstResultados = null;
		if (ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_DNI.equals(request.getTipoConsulta())) {
			lstResultados = consultasPorDni(cuo, lstDataSource, request.getDocumento());
		} else if (ConstantesProject.TIPO_BUSQUEDA_PPREVENTIVA_NOMBRES.equals(request.getTipoConsulta())) {
			lstResultados = consultasPorApellidosNombres(cuo, lstDataSource, request.getApellidoPaterno(), request.getApellidoMaterno(), request.getNombres());
		}
				
		if (null == lstResultados || lstResultados.isEmpty()) {
			response = new ResponseConsultaPrisionPreventivaDTO(ConstantesProject.RPTA_1, "La consulta de prisiones preventivas se realizó con éxito. No se encontraron resultados", lstResultados);
		} else {
			response = new ResponseConsultaPrisionPreventivaDTO(ConstantesProject.RPTA_1, "La consulta de prisiones preventivas se realizó con éxito", lstResultados);
		}
		
		return response;
	}

	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-19
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Realiza la búsqueda de prisiones preventivas a nivel nacional por apellidos y nombres.
	 * </pre>
	 *
	 * @param cuo El código único de operación.
	 * @param lstDataSource La lista de DataSources de todas las bases de datos.
	 * @param numeroDocumento El DNI de la persona.
	 * @return La lista de prisiones preventivas.
	 * @throws Exception La excepción controlada que pueda ocurrir.
	 */
	private List<PrisionPreventivaDTO> consultasPorDni(String cuo, List<DataSourceDTO> lstDataSource, String numeroDocumento) throws Exception {
		List<PrisionPreventivaDTO> lstResultados = new ArrayList<PrisionPreventivaDTO>();
		try {
			Integer size = lstDataSource.size();
			ExecutorService pool = Executors.newFixedThreadPool(size);
			Set<Future<List<PrisionPreventivaDTO>>> lstResHilos = new HashSet<Future<List<PrisionPreventivaDTO>>>(0);
			for (DataSourceDTO dataSource : lstDataSource) {
				log.info("DATASOURCE {}",dataSource.getNombreDB());
				Callable<List<PrisionPreventivaDTO>> callable = new ConsultaPrisionPreventivaPorDniCallable(cuo, 
						numeroDocumento, dataSource, consultaPrisionPreventivaDao);
				Future<List<PrisionPreventivaDTO>> future = pool.submit(callable);
				lstResHilos.add(future);
			}
			pool.shutdown();
			/* Espera hasta que todos los hilos finalicen */
			while (!pool.isTerminated()) {
			}
			
			for (Future<List<PrisionPreventivaDTO>> future : lstResHilos) {
				List<PrisionPreventivaDTO> lstHilo = future.get();
				lstResultados.addAll(lstHilo);
			}
			return lstResultados;
		} catch (Exception e) {
			log.error("Ocurrio un error consultando a RENIEC por DNI: {}", e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-19
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Realiza la búsqueda de prisiones preventivas a nivel nacional por apellidos y nombres.
	 * </pre>
	 *
	 * @param cuo El código único de operación.
	 * @param lstDataSource La lista de DataSources de todas las bases de datos.
	 * @param apellidoPaterno El apellido paterno para la búsqueda.
	 * @param apellidoMaterno El apellido materno para la búsqueda.
	 * @param nombres Los nombres para la búsqueda.
	 * @return La lista de prisiones preventivas.
	 * @throws Exception La excepción controlada que pueda ocurrir.
	 */
	private List<PrisionPreventivaDTO> consultasPorApellidosNombres(String cuo, 
			List<DataSourceDTO> lstDataSource, String apellidoPaterno, String apellidoMaterno, String nombres) throws Exception {
		List<PrisionPreventivaDTO> lstResultados = new ArrayList<PrisionPreventivaDTO>();
		try {
			Integer size = lstDataSource.size();
			ExecutorService pool = Executors.newFixedThreadPool(size);
			Set<Future<List<PrisionPreventivaDTO>>> lstResHilos = new HashSet<Future<List<PrisionPreventivaDTO>>>(0);
			for (DataSourceDTO dataSource : lstDataSource) {
				Callable<List<PrisionPreventivaDTO>> callable = new ConsultaPrisionPreventivaPorApellidosNombresCallable(
						cuo, apellidoPaterno, apellidoMaterno, nombres, dataSource, consultaPrisionPreventivaDao);
				Future<List<PrisionPreventivaDTO>> future = pool.submit(callable);
				lstResHilos.add(future);
			}
			pool.shutdown();
			/* Espera hasta que todos los hilos finalicen */
			while (!pool.isTerminated()) {
			}
			
			for (Future<List<PrisionPreventivaDTO>> future : lstResHilos) {
				List<PrisionPreventivaDTO> lstHilo = future.get();
				lstResultados.addAll(lstHilo);
			}
			return lstResultados;
		} catch (Exception e) {
			log.error("Ocurrio un error consultando a RENIEC por apellidos y nombres: {}", e.getMessage());
			throw e;
		}
	}
}
