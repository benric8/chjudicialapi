/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.service.impl;

import javax.xml.ws.Holder;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniec;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecPortType;
import pe.gob.pj.client.reniec.consultas.ws.ConsultaReniecResponse;
import pe.gob.pj.hjudicial.service.ReniecWsService;

/**
 * <pre>
 * Objeto     : ReniecWsServiceImpl.
 * Descripción: Interface que implementa los métodos que permiten intaractuar con el servicio de RENIEC..
 * Fecha      : 2022-07-18
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-18    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Service("reniecWsService")
public class ReniecWsServiceImpl implements ReniecWsService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConsultaReniecResponse consultaReniec(ConsultaReniecPortType port, ConsultaReniec consultaReniecRequest)
			throws Exception {
		ConsultaReniecResponse response = new ConsultaReniecResponse();
		
		try{
			Holder<String> resTrama = new Holder <String>() ;
			Holder<String> resCodigo = new Holder <String>() ;
			Holder<String> resDescripcion = new Holder <String>();
			Holder<String> resTotalRegistros = new Holder <String>();
			Holder<String> resPersona= new Holder <String>();
			Holder<byte[]> resFoto = new Holder <byte[]>();
			Holder<byte[]> resFirma =  new Holder <byte[]>();
			Holder<String> resListaPersonas = new Holder <String>();
			
			port.consultaReniec(consultaReniecRequest.getReqTrama(), consultaReniecRequest.getReqDniConsultante(), consultaReniecRequest.getReqTipoConsulta(), consultaReniecRequest.getReqUsuario(), consultaReniecRequest.getReqIp(), consultaReniecRequest.getReqDni(), consultaReniecRequest.getReqNombres(), 
					consultaReniecRequest.getReqApellidoPaterno(), consultaReniecRequest.getReqApellidoMaterno(), consultaReniecRequest.getReqNroRegistros(), consultaReniecRequest.getReqGrupo(), consultaReniecRequest.getReqDniApoderado(), consultaReniecRequest.getReqTipoVinculoApoderado(), 
					resTrama, resCodigo, resDescripcion, resTotalRegistros, resPersona, resFoto, resFirma, resListaPersonas);
			
			response.setResCodigo(resCodigo.value);
			response.setResDescripcion(resDescripcion.value);
			response.setResFirma(resFirma.value);
			response.setResFoto(resFoto.value);
			response.setResListaPersonas(resListaPersonas.value);
			response.setResPersona(resPersona.value);
			response.setResTotalRegistros(resTotalRegistros.value);
			response.setResTrama(resTrama.value);
			return response;
		}catch(Exception e){
			log.error("Ha ocurrido un error invocando a RENIEC: {}", e.getMessage());
    		throw e;
		}
	}

}
