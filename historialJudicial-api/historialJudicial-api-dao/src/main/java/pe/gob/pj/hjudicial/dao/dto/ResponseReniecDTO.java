/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <pre>
 * Objeto     : ResponseReniecDTO.
 * Descripción: Clase POJO para manejar la información de las respuestas de la consulta a RENIEC.
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Data
public class ResponseReniecDTO {

	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
}
