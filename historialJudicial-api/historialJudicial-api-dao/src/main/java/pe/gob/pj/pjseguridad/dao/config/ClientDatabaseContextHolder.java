/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.pjseguridad.dao.config;

import org.springframework.util.Assert;

/**
 * <pre>
 * Objeto     : ClientDatabaseContextHolder.
 * Descripción: Clase que permite manejar el contexto de base de datos y así saber a que DataSource real enrutar.
 * Fecha      : 2022-07-13
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-13    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public class ClientDatabaseContextHolder {

	private static ThreadLocal<String> CONTEXT = new ThreadLocal<String>();
	
	public static void set(String clientDatabase) {
		Assert.notNull(clientDatabase, "Referencia cliente de base de datos no puede ser nulo.");
		CONTEXT.set(clientDatabase);
	}
	
	public static String getClientDatabase() {
		return CONTEXT.get();
	}
	
	public static void clear() {
		CONTEXT.remove();
	}
}
