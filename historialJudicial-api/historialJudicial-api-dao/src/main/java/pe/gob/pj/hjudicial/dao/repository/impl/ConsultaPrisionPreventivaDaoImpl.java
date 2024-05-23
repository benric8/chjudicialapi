/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.repository.ConsultaPrisionPreventivaDao;
import pe.gob.pj.hjudicial.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.EdicionSybase;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.hjudicial.dao.utils.QueryUtils;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaDaoImpl.
 * Descripción: Clase de acceso a datos que implementa las operaciones de consulta de prisiones preventivas.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Component("consultaPrisionPreventivaDao")
public class ConsultaPrisionPreventivaDaoImpl implements ConsultaPrisionPreventivaDao {

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PrisionPreventivaDTO> consultaPorDni(String cuo, String numeroDocumento, DataSourceDTO dataSource) throws Exception {
		List<PrisionPreventivaDTO> lstResultado = new ArrayList<PrisionPreventivaDTO>();
		//ClientDatabaseContextHolder.set(dataSource.getCodigoBd());
		StringBuilder procedimientoEjecucion = new StringBuilder();
		try {
			
			StringBuilder urlDatasourse = new StringBuilder();
			urlDatasourse.append("jdbc:sybase:Tds:");
			urlDatasourse.append(dataSource.getIpServidor());
			urlDatasourse.append(":");
			urlDatasourse.append(dataSource.getPuerto());
			urlDatasourse.append("/");
			urlDatasourse.append(dataSource.getNombreDB());		
			
			String claveDesencriptada="";
			
			if(dataSource.getPassword()==null) {
				claveDesencriptada="";
			}else {
				try {
					claveDesencriptada = EncryptUtils.decryptPastFrass(dataSource.getPassword(),ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.PjSeguridad.SECRET_TOKEN).toCharArray());
				}catch(Exception e) {
					e.printStackTrace();
					log.error("{} {}", cuo, "Ocurrió un error al intentar desencriptar la clave de la conexión a la corte.");
					
				
				}
			}
			
			DriverManagerDataSource driverDataSource= new DriverManagerDataSource(); 			
			driverDataSource.setUrl(urlDatasourse.toString());
			driverDataSource.setDriverClassName(dataSource.getDriverClassName());	
			driverDataSource.setUsername(dataSource.getUserName());
			driverDataSource.setPassword(claveDesencriptada);
			
				
				
			JdbcTemplate jdbcTemplate = new JdbcTemplate(driverDataSource);
			
			
			Object[] params = {
					numeroDocumento
					};
			
			int[] tipo = { 
					Types.VARCHAR 
					};
			
			String esquema = EdicionSybase.getEdicionPorNombre(dataSource.getEsquema()).getEsquema();
			String sqlPrisionPreventiva = "exec ".concat(esquema).concat(".").concat(QueryUtils.Sij.QUERY_CONSULTAR_PRISION_PREVENTIVA_DNI);
			
			log.info("{} Conexión Corte:  {}", cuo, dataSource.getNomSistema());
			log.info("{} Ejecución: {}",cuo,sqlPrisionPreventiva);
			log.info("{} Parámetros: {}",cuo,Arrays.toString(params));
			
			procedimientoEjecucion.append(ConstantesProject.Procedimiento.SP_CONSULTAR_PRISION_PREVENTIVA);
			
			
			lstResultado = jdbcTemplate.query(sqlPrisionPreventiva, params, tipo,
					(rs, rowNum) -> {
						PrisionPreventivaDTO row = new PrisionPreventivaDTO();
						row.setNUnico(UtilsProject.isNullOrEmpty(rs.getLong("nUnico"))?0L:rs.getLong("nUnico"));
						row.setNIncidente(UtilsProject.isNullOrEmpty(rs.getInt("nIncidente"))?0:rs.getInt("nIncidente"));
						row.setExpediente(StringUtils.defaultString(rs.getString("expediente")));
						row.setDni(StringUtils.defaultString(rs.getString("dni")));
						row.setTipoParte(StringUtils.defaultString(rs.getString("tipoParte")));
						row.setNombres(StringUtils.defaultString(rs.getString("nombres")));
						row.setDelito(StringUtils.defaultString(rs.getString("delito")));
						row.setMedida(StringUtils.defaultString(rs.getString("tipoIncidente")));
						row.setEstado(StringUtils.defaultString(rs.getString("estado")));
						row.setResDictaPrisionPreventiva(StringUtils.defaultString(rs.getString("resDictaPrisionPreventiva")));
						row.setFechaDictaPrisionPreventina(StringUtils.defaultString(rs.getString("fechaDictaPrisionPreventiva")));
						row.setMedidaCoercitiva(StringUtils.defaultString(rs.getString("medidaCoercitiva")));
						row.setFechaInicio(StringUtils.defaultString(rs.getString("inicioMedidaCoercitiva")));
						row.setFechaFin(StringUtils.defaultString(rs.getString("finMedidaCoercitiva")));
						return row;
					});
			
			
		} catch (Exception e) {
			log.error("cuo, Ocurrio un error consultando al SP usp_ListadoPrisionPrevDni de la base de datos: {}", cuo, dataSource.getCodigoBd());
			log.error("{} Detalle del error: {}", cuo, e.getMessage());
		} 
		return lstResultado;
	}

	@Override
	public List<PrisionPreventivaDTO> consultaPorApellidosYNombres(String cuo, String apellidoPaterno,
			String apellidoMaterno, String nombres, DataSourceDTO dataSource) throws Exception {
		List<PrisionPreventivaDTO> lstResultado = new ArrayList<PrisionPreventivaDTO>();
		StringBuilder procedimientoEjecucion = new StringBuilder();
		String tipoBusqueda = "%";
		try {
			
			StringBuilder urlDatasourse = new StringBuilder();
			urlDatasourse.append("jdbc:sybase:Tds:");
			urlDatasourse.append(dataSource.getIpServidor());
			urlDatasourse.append(":");
			urlDatasourse.append(dataSource.getPuerto());
			urlDatasourse.append("/");
			urlDatasourse.append(dataSource.getNombreDB());		
			
			String claveDesencriptada="";
			
			if(dataSource.getPassword()==null) {
				claveDesencriptada="";
			}else {
				try {
					claveDesencriptada = EncryptUtils.decryptPastFrass(dataSource.getPassword(),ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.PjSeguridad.SECRET_TOKEN).toCharArray());
				}catch(Exception e) {
					e.printStackTrace();
					log.error("{} {}", cuo, "Ocurrió un error al intentar desencriptar la clave de la conexión a la corte.");
					
				
				}
			}
			
			DriverManagerDataSource driverDataSource= new DriverManagerDataSource(); 			
			driverDataSource.setUrl(urlDatasourse.toString());
			driverDataSource.setDriverClassName(dataSource.getDriverClassName());	
			driverDataSource.setUsername(dataSource.getUserName());
			driverDataSource.setPassword(claveDesencriptada);
			
				
				
			JdbcTemplate jdbcTemplate = new JdbcTemplate(driverDataSource);
			
			
			Object[] params = {
					tipoBusqueda , apellidoPaterno, apellidoMaterno, nombres
					};
			
			int[] tipo = { 
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR 
					};
			
			String esquema = EdicionSybase.getEdicionPorNombre(dataSource.getEsquema()).getEsquema();
			String sqlPrisionPreventiva = "exec ".concat(esquema).concat(".").concat(QueryUtils.Sij.QUERY_CONSULTAR_PRISION_PREVENTIVA_NOMBRES);
			
			log.info("{} Conexión Corte:  {}", cuo, dataSource.getNomSistema());
			log.info("{} Ejecución: {}",cuo,sqlPrisionPreventiva);
			log.info("{} Parámetros: {}",cuo,Arrays.toString(params));
			
			procedimientoEjecucion.append(ConstantesProject.Procedimiento.SP_CONSULTAR_PRISION_PREVENTIVA);
			
			
			lstResultado = jdbcTemplate.query(sqlPrisionPreventiva, params, tipo,
					(rs, rowNum) -> {
						PrisionPreventivaDTO row = new PrisionPreventivaDTO();
						row.setNUnico(UtilsProject.isNullOrEmpty(rs.getLong("nUnico"))?0L:rs.getLong("nUnico"));
						row.setNIncidente(UtilsProject.isNullOrEmpty(rs.getInt("nIncidente"))?0:rs.getInt("nIncidente"));
						row.setExpediente(StringUtils.defaultString(rs.getString("expediente")));
						row.setDni(StringUtils.defaultString(rs.getString("dni")));
						row.setTipoParte(StringUtils.defaultString(rs.getString("tipoParte")));
						row.setNombres(StringUtils.defaultString(rs.getString("nombres")));
						row.setDelito(StringUtils.defaultString(rs.getString("delito")));
						row.setMedida(StringUtils.defaultString(rs.getString("tipoIncidente")));
						row.setEstado(StringUtils.defaultString(rs.getString("estado")));
						row.setResDictaPrisionPreventiva(StringUtils.defaultString(rs.getString("resDictaPrisionPreventiva")));
						row.setFechaDictaPrisionPreventina(StringUtils.defaultString(rs.getString("fechaDictaPrisionPreventiva")));
						row.setMedidaCoercitiva(StringUtils.defaultString(rs.getString("medidaCoercitiva")));
						row.setFechaInicio(StringUtils.defaultString(rs.getString("inicioMedidaCoercitiva")));
						row.setFechaFin(StringUtils.defaultString(rs.getString("finMedidaCoercitiva")));
						return row;
					});
			
			
		} catch (Exception e) {
			log.error("cuo, Ocurrio un error consultando al SP usp_ListadoPrisionPrevDni de la base de datos: {}", cuo, dataSource.getCodigoBd());
			log.error("{} Detalle del error: {}", cuo, e.getMessage());
		} 
		return lstResultado;
						
		
	}


	
}
