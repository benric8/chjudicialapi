/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.pjseguridad.dao.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <pre>
 * Objeto     : ClientDataSourceRouter.
 * Descripción: Clase que permite devolver el DataSource apropiado de forma dinámica.
 * Fecha      : 2022-07-13
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-13    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public class ClientDataSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return ClientDatabaseContextHolder.getClientDatabase();
	}

}
