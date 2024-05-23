/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <pre>
 * Objeto     : RequestReniecDTO.
 * Descripción: Clase POJO que tiene los datos para las consultas a RENIEC.
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Data
public class RequestReniecDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String dni;

}
