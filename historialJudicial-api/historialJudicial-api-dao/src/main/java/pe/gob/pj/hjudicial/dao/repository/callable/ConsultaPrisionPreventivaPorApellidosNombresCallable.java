/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.repository.callable;

import java.util.List;
import java.util.concurrent.Callable;

import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.repository.ConsultaPrisionPreventivaDao;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaPorApellidosNombresCallable.
 * Descripción: Clase que permite realizar las consultas de prisión preventiva por apellidos y nombres de forma concurrente.
 * Fecha      : 2022-07-15
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-15    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
public class ConsultaPrisionPreventivaPorApellidosNombresCallable implements Callable<List<PrisionPreventivaDTO>> {
	
	private ConsultaPrisionPreventivaDao consultaPrisionPreventivaDao;
	
	private String cuo;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private DataSourceDTO dataSource;
	
	public ConsultaPrisionPreventivaPorApellidosNombresCallable(String cuo, String apellidoPaterno, String apellidoMaterno, String nombres, 
			DataSourceDTO dataSource, ConsultaPrisionPreventivaDao consultaPrisionPreventivaDao) {
		this.cuo = cuo;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombres = nombres;
		this.dataSource = dataSource;
		this.consultaPrisionPreventivaDao = consultaPrisionPreventivaDao;
	}

	@Override
	public List<PrisionPreventivaDTO> call() throws Exception {
		return consultaPrisionPreventivaDao.consultaPorApellidosYNombres(cuo, apellidoPaterno, apellidoMaterno, nombres, dataSource);
	}

}
