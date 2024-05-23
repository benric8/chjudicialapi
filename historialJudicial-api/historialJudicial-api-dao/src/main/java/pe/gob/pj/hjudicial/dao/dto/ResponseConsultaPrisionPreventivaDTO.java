/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;

/**
 * <pre>
 * Objeto     : ResponseConsultaPrisionPreventivaDTO.
 * Descripción: Clase POJO para manejar la información de las consultas de prisión preventiva.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Data @AllArgsConstructor
public class ResponseConsultaPrisionPreventivaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String indicador;
	private String mensaje;
	private List<PrisionPreventivaDTO> prisionesPreventivas;
}
