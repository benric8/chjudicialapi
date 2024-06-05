/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.service;

/*import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniec;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecPortType;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecResponse;*/
import pe.gob.pj.hjudicial.restclient.reniec.PersonaDTO;

/**
 * <pre>
 * Objeto     : ReniecWsService.
 * Descripción: Interface que define los métodos que permiten intaractuar con el servicio de RENIEC.
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public interface ReniecWsService {

	/**
	 * 
	 * <pre>
	 * #1
	 * Fecha       : 2022-07-18
	 * Autor       : CALTAMIRANOME.
	 * Descripción : Consulta a RENIC los datos de una persona.
	 * </pre>
	 *
	 * @param port El objeto PortType del servicio.
	 * @param consultaReniecRequest Los datos para la consulta.
	 * @return Los datos encontrados
	 * @throws Exception La excepción controlada que pueda ocurrir.
	 */
	//public ConsultaReniecResponse consultaReniec(ConsultaReniecPortType port, ConsultaReniec consultaReniecRequest) throws Exception;
	PersonaDTO obtenerPersonaPorDNI(String dni, String pcName, String ipAddress, String macAddress, String usuario) throws Exception;
	
}
