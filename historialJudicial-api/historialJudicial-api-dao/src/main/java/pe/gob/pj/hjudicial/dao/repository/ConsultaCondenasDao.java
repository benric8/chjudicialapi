package pe.gob.pj.hjudicial.dao.repository;


import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;

public interface ConsultaCondenasDao {
	
	/***
	 * 
	 * @param cuo
	 * @param numeroDocumento
	 * @return Condenas de usuario que coincida con el número de documento
	 * @throws Exception
	 */
	public ResponseConsultaCondenasDTO consultaXDni(String cuo, String numeroDocumento) throws Exception;
	
	/***
	 * 
	 * @param cuo
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param nombres
	 * @return Condenas de usuario que coincida con los apellidos y nombres
	 * @throws Exception
	 */
	public ResponseConsultaCondenasDTO consultaXNombres(String cuo, String apellidoPaterno, String apellidoMaterno, String nombres) throws Exception;
}
