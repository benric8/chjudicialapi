/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <pre>
 * Objeto     : RequestConsultaPrisionPreventivaDTO.
 * Descripción: Clase POJO para manejar los datos de entrada para las consultas de prisión preventiva.
 * Fecha      : 2022-07-15
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-15    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Data
public class RequestConsultaPrisionPreventivaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String documento;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	
	private String tipoConsulta;
	
}
