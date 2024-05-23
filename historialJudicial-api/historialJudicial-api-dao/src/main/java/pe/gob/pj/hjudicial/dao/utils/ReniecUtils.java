/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.utils;

import javax.xml.ws.BindingProvider;

import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecPortType;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecService;

/**
 * <pre>
 * Objeto     : ReniecUtils.
 * Descripción: Clase utilitaria para el consumo del servicio de RENIEC.
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public class ReniecUtils {

	public static ConsultaReniecPortType getPortReniec(String endpoint, int timeout) {
		ConsultaReniecService service = new ConsultaReniecService();
		ConsultaReniecPortType port = service.getConsultaReniec();
		
		BindingProvider bp = (BindingProvider)port;
		// Se asigna el endpoint
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		// Se asigna los tiempos de timeout de la consulta al WS
		bp.getRequestContext().put("com.sun.xml.ws.connect.timeout", timeout * 1000);
		bp.getRequestContext().put("com.sun.xml.ws.request.timeout", timeout * 1000);
		return port;
	}
}
