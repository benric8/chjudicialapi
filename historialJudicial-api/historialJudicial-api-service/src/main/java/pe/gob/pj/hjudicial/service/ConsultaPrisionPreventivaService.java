/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.service;

import pe.gob.pj.hjudicial.dao.dto.RequestConsultaPrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaPrisionPreventivaDTO;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaService.
 * Descripción: Interfaz que define las operaciones de consulta de prisiones preventivas.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public interface ConsultaPrisionPreventivaService {

	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-14
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Devuelve las prisiones preventivas según el DNI ingresado.
	 * </pre>
	 *
	 * @param cuo El código único de operación.
	 * @param request Los datos de entrada para la consulta.
	 * @param ipRemoto El IP remoto.
	 * @return La lista de prisiones preventivas.
	 * @throws Exception La excepción controlada que pueda existir.
	 */
	public ResponseConsultaPrisionPreventivaDTO consultaPrisionesPreventivas(String cuo, RequestConsultaPrisionPreventivaDTO request, String ipRemoto) throws Exception;
}
