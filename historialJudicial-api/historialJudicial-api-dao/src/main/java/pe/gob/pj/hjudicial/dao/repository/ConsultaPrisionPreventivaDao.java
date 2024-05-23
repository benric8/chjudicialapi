/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.repository;

import java.util.List;

import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaDao.
 * Descripción: Interfaz de acceso a datos que define las operaciones de consulta de prisiones preventivas.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public interface ConsultaPrisionPreventivaDao {

	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-15
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Devuelve las prisiones preventivas según el DNI ingresado.
	 * </pre>
	 *
	 * @param cuo El código único de operación.
	 * @param numeroDocumento El número de documento (DNI).
	 * @param dataSource El objeto que tiene el código de base de datos y esquema.
	 * @return La lista de prisiones preventivas.
	 * @throws Exception La excepción controlada que pueda existir.
	 */
	public List<PrisionPreventivaDTO> consultaPorDni(String cuo, String numeroDocumento, DataSourceDTO dataSource) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-15
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Devuelve las prisiones preventivas según nombres y apellidos.
	 * </pre>
	 *
	 * @param cuo El código único de operación.
	 * @param apellidoPaterno El apellido paterno de la persona a buscar.
	 * @param apellidoMaterno El apellido materno de la persona a buscar.
	 * @param nombres Los nombres de la persona a buscar.
	 * @param dataSource El objeto que tiene el código de base de datos y esquema.
	 * @return La lista de prisiones preventivas.
	 * @throws Exception La excepción controlada que pueda existir.
	 */
	public List<PrisionPreventivaDTO> consultaPorApellidosYNombres(String cuo, String apellidoPaterno, String apellidoMaterno, String nombres, DataSourceDTO dataSource) throws Exception;
	
}
