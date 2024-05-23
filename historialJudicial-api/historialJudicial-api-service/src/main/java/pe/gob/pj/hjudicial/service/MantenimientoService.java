package pe.gob.pj.hjudicial.service;

import java.util.Map;

import pe.gob.pj.hjudicial.dao.dto.ResponseEntidadesDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponsePerfilesDTO;

public interface MantenimientoService {

	public ResponseEntidadesDTO obtenerEntidades(String cuo, Map<String, Object> filters) throws Exception;
	public void crearEntidad(String cuo, EntidadDTO entidadI) throws Exception;
	public void actualizarEntidad(String cuo, EntidadDTO entidadU) throws Exception;
	public ResponsePerfilesDTO obtenerPerfiles(String cuo) throws Exception;
}
