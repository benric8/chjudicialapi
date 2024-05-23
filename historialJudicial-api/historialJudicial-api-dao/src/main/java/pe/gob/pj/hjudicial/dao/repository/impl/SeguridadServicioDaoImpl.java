package pe.gob.pj.hjudicial.dao.repository.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.hjudicial.dao.dto.seguridad.MovUsuario;
import pe.gob.pj.hjudicial.dao.entity.seguridad.MaeRol;
import pe.gob.pj.hjudicial.dao.entity.seguridad.MaeRolUsuario;
import pe.gob.pj.hjudicial.dao.entity.seguridad.MaeUsuario;
import pe.gob.pj.hjudicial.dao.repository.impl.SeguridadServicioDaoImpl;
import pe.gob.pj.hjudicial.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.EncryptUtils;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.dao.repository.SeguridadServicioDao;

@Component("seguridadServicioDao")
public class SeguridadServicioDaoImpl implements SeguridadServicioDao, Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SeguridadServicioDaoImpl.class);
		
	@Autowired
	@Qualifier("sessionSeguridad")
	private SessionFactory sf;

	@Override
	public String autenticarUsuario(String codigoCliente, String codigoRol, String usuario, String clave,String cuo) throws Exception {
		MovUsuario user = new MovUsuario();
		String nsAplicativo = ConfiguracionPropiedades.getInstance().getProperty(ConstantesProject.Seguridad.ID_APLICATIVO);
		int nAplicacion = UtilsProject.isNullOrEmpty(nsAplicativo)?0:Integer.parseInt(nsAplicativo) ;
		Object[] params = { usuario, codigoRol, nAplicacion, codigoCliente };
		try {			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.AUTENTICAR_USUARIO, MaeUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, codigoRol);
			query.setParameter(MaeRolUsuario.P_COD_CLIENTE, codigoCliente);
			query.setParameter(MaeRolUsuario.P_N_APLICATIVO, nAplicacion);
			MaeUsuario usr =  query.getSingleResult();
			String claveFinal = EncryptUtils.encrypt(usuario, clave);
			if(UtilsProject.isNull(usr.getCClave()).trim().equals(claveFinal)) {
				user.setId(usr.getNUsuario());
				user.setcClave(UtilsProject.isNull(usr.getCClave()));
			}
		} catch (NoResultException not) {
			logger.info(cuo.concat("No se encontró usuario registrado en la base de datos.").concat(params.toString()));
		} catch (Exception e) {
			logger.error(cuo.concat(e.getMessage()));
		}
		return user.getId() == null? null: user.getId().toString();
	}
	
	@Override
	public MovUsuario recuperaInfoUsuario(String id, String cuo) throws Exception {
		MovUsuario user = new MovUsuario();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.FIND_BY_ID, MaeUsuario.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			MaeUsuario u = query.getSingleResult();
			user.setcClave(u.getCClave());
			user.setcUsuario(u.getCUsuario());
			user.setId(u.getNUsuario());
			user.setlActivo(u.getLActivo());
		} catch (NoResultException not) {
			logger.info(cuo.concat("No se encontró usuario registrado en la base de datos.").concat(params.toString()));
			user = null;
		} catch (Exception e) {
			logger.error(cuo.concat(e.getMessage()));
			user = null;
		}
		return user;
	}
	
	@Override
	public List<MaeRol> recuperarRoles(String id, String cuo) throws Exception {
		List<MaeRol> lista = new ArrayList<MaeRol>();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeRol> query = this.sf.getCurrentSession().createNamedQuery(MaeRol.FIND_ROLES_BY_ID_USUARIO, MaeRol.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			lista = query.getResultList();
		} catch (NoResultException not) {
			logger.info(cuo.concat("No se encontró roles registrados en la base de datos.").concat(params.toString()));
			lista = null;
		} catch (Exception e) {
			logger.error(cuo.concat(e.getMessage()));
			lista = null;
		}
		return lista;
	}

	@Override
	public boolean validarAccesoMetodo(String usuario, String rol, String operacion, String cuo) throws Exception {
		boolean rpta = false;
		Object[] params = { usuario,rol,operacion };
		try {
			TypedQuery<MaeRolUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.VALIDAR_ACCESO_METODO , MaeRolUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, rol);
			query.setParameter(MaeRolUsuario.P_OPERACION, operacion);
			rpta = query.getResultList().size()>0;
		} catch (NoResultException not) {
			logger.info(cuo.concat("No se encontró permiso a la operación con el rol del usuario").concat(params.toString()));
			rpta = false;
		} catch (Exception e) {
			logger.error(cuo.concat(e.getMessage()));
			rpta = false;
		}		
		return rpta;
	}
}
