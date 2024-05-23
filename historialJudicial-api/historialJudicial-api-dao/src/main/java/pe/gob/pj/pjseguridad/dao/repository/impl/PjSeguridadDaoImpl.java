/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.pjseguridad.dao.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;
import pe.gob.pj.pjseguridad.dao.repository.PjSeguridadDao;
import pe.gob.pj.pjseguridad.util.PjSeguridadConstantes;

/**
 * <pre>
 * Objeto     : PjSeguridadDaoImpl.
 * Descripción: Clase de acceso a datos que implementa los métodos definidos en la interfaz para interactuar con la base de datos PjSeguridad.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Component("pjSeguridadDao")
public class PjSeguridadDaoImpl implements PjSeguridadDao {

	@Autowired
	@Qualifier("pjSeguridadJdbcTemplate")
	private JdbcTemplate pjSeguridadJdbcTemplate;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataSourceDTO> obtenerDataSources() throws Exception {
		StringBuilder sql = new StringBuilder();
		List<DataSourceDTO> lstResultado = new ArrayList<DataSourceDTO>();
		try {
			sql.append("SELECT cx.n_idConexion, s.x_nombre AS nomAplicativo, ");
			sql.append("b.x_edicion, b.x_ipServidor, b.x_puerto, ");
			sql.append("b.x_nombre, cx.x_usuario, cx.x_clave, ");
			sql.append("ISNULL(b.n_id_bd_estadistica, 0) AS codigo_bd, ");
			sql.append("'com.sybase.jdbc4.jdbc.SybDriver' AS driver ");
			sql.append("FROM uid_seguridad.Mae_BaseDatos b ");
			sql.append("INNER JOIN uid_seguridad.Mae_Conexion cx ON cx.n_idBaseDatos=b.n_idBaseDatos ");
			sql.append("INNER JOIN uid_seguridad.Cfg_SistemaConexion sic ON sic.n_idConexion=cx.n_idConexion ");
			sql.append("INNER JOIN uid_seguridad.Mae_Sistema s ON s.n_idSistema=sic.n_idSistema ");
			sql.append("WHERE s.x_nombre = '");
			sql.append(PjSeguridadConstantes.NOMBRE_APLICACION).append("' ");
			sql.append("AND b.n_id_bd_estadistica is not null ");
			sql.append("AND b.l_estadoRegistro = '1' ");
			sql.append("AND cx.l_estadoRegistro = '1' ");
			sql.append("AND sic.l_estadoRegistro = '1' ");
			sql.append("AND s.l_estadoRegistro = '1' ");
			
			List<Map<String, Object>> rows = pjSeguridadJdbcTemplate.queryForList(sql.toString());
			for (Map<String, Object> row : rows) {
				String puerto = String.valueOf(row.get("x_puerto"));
				if (!StringUtils.isNumeric(puerto)) {
					continue;
				}
				DataSourceDTO dataSource = new DataSourceDTO();
				dataSource.setIdConexion(Long.parseLong((String)row.get("n_idConexion").toString()));
				dataSource.setNomSistema((String) row.get("nomAplicativo"));
				dataSource.setEsquema(((String) row.get("x_edicion")).equals(PjSeguridadConstantes.TIPO_ASA) ? PjSeguridadConstantes.TIPO_SQUEMA_ASA: PjSeguridadConstantes.TIPO_SQUEMA_ASE);
				dataSource.setIpServidor((String) row.get("x_ipServidor"));
				dataSource.setPuerto(puerto);
				dataSource.setNombreDB((String) row.get("x_nombre"));
				dataSource.setUserName((String) row.get("x_usuario"));
				dataSource.setPassword(EncryptUtils.decryptPastFrass((String) row.get("x_clave")));
				dataSource.setCodigoBd(String.valueOf(row.get("codigo_bd")));
				dataSource.setDriverClassName((String) row.get("driver"));
				lstResultado.add(dataSource);
			}
			return lstResultado;
		} catch (Exception e) {
			log.error("No se ha podido obtener la lista de DataSource de la base de datos pjSeguridad: Detalle del error: {}", e.getMessage());
			throw e;
		}
	}

}
