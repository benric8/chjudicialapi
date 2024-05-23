/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.pjseguridad.dao.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Objeto     : DataSourceDto.
 * Descripción: Bean que mantiene la información de los DataSource.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Getter @Setter
public class DataSourceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idConexion;
	private String nomSistema;
	private String ipServidor;
	private String puerto;
	private String nombreDB;
	private String esquema;
	private String userName;
	private String password;
	
	private String codigoBd;
	private String driverClassName;
	
}
