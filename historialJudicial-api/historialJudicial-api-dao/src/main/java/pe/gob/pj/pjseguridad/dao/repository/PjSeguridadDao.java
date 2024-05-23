/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.pjseguridad.dao.repository;

import java.util.List;

import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;

/**
 * <pre>
 * Objeto     : PjSeguridadDao.
 * Descripción: Interface de acceso a datos para interactuar con la base de datos PjSeguridad.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public interface PjSeguridadDao {

	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-14
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Devuelve la lista de DataSources
	 * </pre>
	 *
	 * @return La lista de DataSources
	 * @throws Exception La excepción controlada que pueda ocurrir.
	 */
	public List<DataSourceDTO> obtenerDataSources() throws Exception;
	
}
