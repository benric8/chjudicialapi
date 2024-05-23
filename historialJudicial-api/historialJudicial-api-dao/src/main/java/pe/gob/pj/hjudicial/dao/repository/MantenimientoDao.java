package pe.gob.pj.hjudicial.dao.repository;

import java.util.Map;

import pe.gob.pj.hjudicial.dao.dto.ResponseEntidadesDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponsePerfilesDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;

public interface MantenimientoDao {

	public ResponseEntidadesDTO obtenerEntidades(String cuo, Map<String, Object> filters) throws Exception;
	public void crearEntidad(String cuo, EntidadDTO entidadI) throws Exception;
	public void actualizarEntidad(String cuo, EntidadDTO entidadU) throws Exception;
	public ResponsePerfilesDTO obtenerPerfiles(String cuo) throws Exception;
}
