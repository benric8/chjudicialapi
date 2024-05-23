package pe.gob.pj.hjudicial.service;

import pe.gob.pj.hjudicial.dao.dto.RequestConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;

public interface ConsultaCondenasService {
	/***
	 * 
	 * @param cuo
	 * @param numeroDocumento
	 * @return Condenas de persona que coincida con el número documento
	 * @throws Exception
	 */
	public ResponseConsultaCondenasDTO consultaCondenas(String cuo, RequestConsultaCondenasDTO filtro) throws Exception;
}
