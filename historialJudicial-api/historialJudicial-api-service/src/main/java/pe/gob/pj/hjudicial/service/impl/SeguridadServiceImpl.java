package pe.gob.pj.hjudicial.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.hjudicial.dao.repository.SeguridadServicioDao;
import pe.gob.pj.hjudicial.service.SeguridadService;

@Service("seguridadService")
public class SeguridadServiceImpl implements SeguridadService, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SeguridadServiceImpl.class);
	
	
	@Autowired
	private SeguridadServicioDao seguridadServicioDao;

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public String autenticarUsuario(String codigoCliente, String codigoAplicativo, String usuario, String clave, String cuo) throws Exception {
		String result = null;
		Long tiempoInicial = System.currentTimeMillis();
		try {			
			result = seguridadServicioDao.autenticarUsuario(codigoCliente, codigoAplicativo, usuario, clave, cuo);  
			if (result.equals(null) || result.isEmpty()) {
				result =null;
				throw new Exception("Resultado nulo o vacio");
			}
		} catch (Exception ex) {
			logger.error(cuo+(ex.getMessage()));
			throw ex;
		} finally {
			logger.info(cuo+"****** PROCESO SRV M-SEG 01 DURACIÓN=[{}(ms)]", (System.currentTimeMillis() - tiempoInicial)+" ******");
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public boolean validarAccesoMetodo(String usuario, String rol, String ruta, String cuo) throws Exception {
		return seguridadServicioDao.validarAccesoMetodo(usuario, rol, ruta, cuo);
	}
	
}