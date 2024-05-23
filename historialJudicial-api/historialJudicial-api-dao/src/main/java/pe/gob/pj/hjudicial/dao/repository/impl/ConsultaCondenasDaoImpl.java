package pe.gob.pj.hjudicial.dao.repository.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.consultas.CondenaDTO;
import pe.gob.pj.hjudicial.dao.procedures.ProcedureRnc;
import pe.gob.pj.hjudicial.dao.repository.ConsultaCondenasDao;
import pe.gob.pj.hjudicial.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;

/**
 * Objeto     : ConsultaCondenasDaoImpl.
 * Descripción: Clase que implementa las operaciones dao de opciones de consulta de condenas.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@Component("consultaCondenasDao")
public class ConsultaCondenasDaoImpl implements Serializable, ConsultaCondenasDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ConsultaCondenasDaoImpl.class);
	
	@Autowired
	@Qualifier("jdbcTemplateCondenas")
	protected JdbcTemplate jdbcCondenas;

	@Override
	public ResponseConsultaCondenasDTO consultaXDni(String cuo, String numeroDocumento) throws Exception {
		ResponseConsultaCondenasDTO responseConsulta = new ResponseConsultaCondenasDTO();
		try {
			
			List<CondenaDTO> condenas = new ArrayList<CondenaDTO>();
			int tipoConsulta = 0;
			StringBuilder sql = new StringBuilder();
			sql.append(ProcedureRnc.QUERY_CONSULTAR_CONDENAS_DNI);
			
			//Object[] params = {numeroDocumento};
			//int[] tipo = { Types.VARCHAR};
			Object[] params = {numeroDocumento,"","","",tipoConsulta};
			int[] tipo = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
			
			condenas = this.jdbcCondenas.query(sql.toString(), params, tipo, 
					(rs, rowNum) -> {
						int condMaxDiasCumplimiento = UtilsProject.isNullOrEmpty(ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Config.PARAM_CONFIG_MOSTRAR_CONDENA_MAX_DIAS))?3650:Integer.parseInt(ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Config.PARAM_CONFIG_MOSTRAR_CONDENA_MAX_DIAS));
						int diasCumplido = UtilsProject.isNullOrEmpty(rs.getInt("diasComp"))?0:rs.getInt("diasComp");
						if (diasCumplido<= condMaxDiasCumplimiento) {
								return new CondenaDTO(
										rs.getString("numeroIdentSol"),
										rs.getString("apellidoPaternoSol") + (!UtilsProject.isNullOrEmpty(rs.getString("apellidoMaternoSol")) ? " " + rs.getString("apellidoMaternoSol") + "," : " ,") + rs.getString("nombresSol"),
										rs.getString("descPena"),
										rs.getString("delito"),
										(UtilsProject.isNullOrEmpty(rs.getString("annoPena")) ? "0" : rs.getString("annoPena")) + " AÑO(S)," +
												(UtilsProject.isNullOrEmpty(rs.getString("mesPena")) ? "0" : rs.getString("mesPena")) + " MES(ES)," +
												(UtilsProject.isNullOrEmpty(rs.getString("diasPena")) ? "0" : rs.getString("diasPena")) + " DIAS," ,
										UtilsProject.isNull(rs.getString("fechaPronunBoletn")),
										UtilsProject.isNull(rs.getString("fechaPronunBoletn")),
										UtilsProject.isNull(rs.getString("fechaFinPenaComp")),
										UtilsProject.isNullOrEmpty(rs.getString("rehabilitado")) ? "NO" : rs.getString("rehabilitado").equalsIgnoreCase(ConstantesProject.LETRA_S) ? "SI" : "NO" ,
										rs.getLong("boletin"),
										rs.getInt("nTipoBoletin"),
										rs.getString("numArticulo"),
										rs.getLong("jornadas"),
										rs.getLong("xDiasMulta"),
										rs.getInt("diasComp"),
										rs.getString("tipoBoletin")
								);
							} else {
								return null;
							}
					}).stream().filter(Objects::nonNull).collect(Collectors.toList());

			responseConsulta.setIndicador(ConstantesProject.RPTA_1);
			responseConsulta.setMensaje("Consulta de condenas por número de documento exitoso.");
			responseConsulta.setCondenas(condenas);
		} catch (Exception e) {
			logger.error("{} Error dao consultaXDni: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		
		return responseConsulta;
	}

	@Override
	public ResponseConsultaCondenasDTO consultaXNombres(String cuo, String apellidoPaterno, String apellidoMaterno, String nombres) throws Exception {
		ResponseConsultaCondenasDTO responseConsulta = new ResponseConsultaCondenasDTO();
		try {
			
			List<CondenaDTO> condenas = new ArrayList<CondenaDTO>();
			int tipoConsulta = 1;
			StringBuilder sql = new StringBuilder();
			sql.append(ProcedureRnc.QUERY_CONSULTAR_CONDENAS_NOMBRES);
			
			//Object[] params = {apellidoPaterno,apellidoMaterno,nombres};
			//int[] tipo = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};

			Object[] params = {"",apellidoPaterno,apellidoMaterno,nombres,tipoConsulta};
			int[] tipo = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
			
			condenas = this.jdbcCondenas.query(sql.toString(), params, tipo, 
					(rs, rowNum) -> {
						int condMaxDiasCumplimiento = UtilsProject.isNullOrEmpty(ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Config.PARAM_CONFIG_MOSTRAR_CONDENA_MAX_DIAS))?3650:Integer.parseInt(ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Config.PARAM_CONFIG_MOSTRAR_CONDENA_MAX_DIAS));
						int diasCumplido = UtilsProject.isNullOrEmpty(rs.getInt("diasComp"))?0:rs.getInt("diasComp");
						if (diasCumplido<= condMaxDiasCumplimiento) {
							return new CondenaDTO(
									rs.getString("numeroIdentSol"),
									rs.getString("apellidoPaternoSol") + (!UtilsProject.isNullOrEmpty(rs.getString("apellidoMaternoSol")) ? " " + rs.getString("apellidoMaternoSol") + "," : " ,") + rs.getString("nombresSol"),
									rs.getString("descPena"),
									rs.getString("delito"),
									(UtilsProject.isNullOrEmpty(rs.getString("annoPena")) ? "0" : rs.getString("annoPena")) + " AÑO(S)," +
											(UtilsProject.isNullOrEmpty(rs.getString("mesPena")) ? "0" : rs.getString("mesPena")) + " MES(ES)," +
											(UtilsProject.isNullOrEmpty(rs.getString("diasPena")) ? "0" : rs.getString("diasPena")) + " DIAS," ,
									UtilsProject.isNull(rs.getString("fechaPronunBoletn")),
									UtilsProject.isNull(rs.getString("fechaPronunBoletn")),
									UtilsProject.isNull(rs.getString("fechaFinPenaComp")),
									UtilsProject.isNullOrEmpty(rs.getString("rehabilitado")) ? "NO" : rs.getString("rehabilitado").equalsIgnoreCase(ConstantesProject.LETRA_S) ? "SI" : "NO" ,
									rs.getLong("boletin"),
									rs.getInt("nTipoBoletin"),
									rs.getString("numArticulo"),
									rs.getLong("jornadas"),
									rs.getLong("xDiasMulta"),
									rs.getInt("diasComp"),
									rs.getString("tipoBoletin")
							);
						} else {
							return null;
						}
					}).stream().filter(Objects::nonNull).collect(Collectors.toList());
			
			responseConsulta.setIndicador(ConstantesProject.RPTA_1);
			responseConsulta.setMensaje("Consulta de condenas por nombres exitoso.");
			responseConsulta.setCondenas(condenas);
		} catch (Exception e) {
			logger.error("{} Error dao consultaXNombres: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return responseConsulta;
	}

}
