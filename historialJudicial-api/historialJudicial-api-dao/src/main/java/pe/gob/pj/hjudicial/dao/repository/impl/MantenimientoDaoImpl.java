package pe.gob.pj.hjudicial.dao.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.hjudicial.dao.dto.ResponseEntidadesDTO;
import pe.gob.pj.hjudicial.dao.dto.ResponsePerfilesDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.EntidadDTO;
import pe.gob.pj.hjudicial.dao.dto.historialjudicial.PerfilDTO;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaeEntidad;
import pe.gob.pj.hjudicial.dao.entity.historialjudicial.MaePerfil;
import pe.gob.pj.hjudicial.dao.repository.MantenimientoDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;

@Component("mantenimientoDao")
public class MantenimientoDaoImpl implements MantenimientoDao,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(MantenimientoDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionHJudicial")
	private SessionFactory sf;	
	
	@Override
	public ResponseEntidadesDTO obtenerEntidades(String cuo, Map<String, Object> filters) throws Exception {
		ResponseEntidadesDTO rspt = new ResponseEntidadesDTO();
		try {
			if(!UtilsProject.isNullOrEmpty(filters.get(EntidadDTO.P_ID_ENTIDAD))) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ID)
					.setParameter(MaeEntidad.P_ID, filters.get(EntidadDTO.P_ID_ENTIDAD));
			}
			if(!UtilsProject.isNullOrEmpty(filters.get(EntidadDTO.P_ENTIDAD))) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ENTIDAD)
					.setParameter(MaeEntidad.P_ENTIDAD, filters.get(EntidadDTO.P_ENTIDAD));
			}
			if(!UtilsProject.isNullOrEmpty(filters.get(EntidadDTO.P_ACTIVO_ENTIDAD))) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ACTIVO)
					.setParameter(MaeEntidad.P_ACTIVO, filters.get(EntidadDTO.P_ACTIVO_ENTIDAD));
			}			
			List<EntidadDTO> lista = new ArrayList<EntidadDTO>();
			TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.NQ_BY_FILTERS, MaeEntidad.class);
			query.getResultStream().forEach(entidad->{
				EntidadDTO entidadDTO = new EntidadDTO();
				entidadDTO.setId(entidad.getNEntidad());
				entidadDTO.setNombreEntidad(entidad.getNombre());
				entidadDTO.setNumeroDocumento(entidad.getDocumento());
				entidadDTO.setActivo(entidad.getActivo());
				lista.add(entidadDTO);
			});
			
			rspt.setIndicador(ConstantesProject.RPTA_1);
			rspt.setMensaje("Operación exitosa.");
			rspt.setEntidades(lista);
			
		}catch(Exception e) {
			logger.error("{} Error dao obtenerEntidades: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return rspt;
	}

	@Override
	public void crearEntidad(String cuo, EntidadDTO entidadI) throws Exception {
		try {
			//Validar si existe entidad		
			if(!UtilsProject.isNullOrEmpty(entidadI.getId())) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ID)
					.setParameter(MaeEntidad.P_ID, entidadI.getId());
			}
			if(!UtilsProject.isNullOrEmpty(entidadI.getNombreEntidad())) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ENTIDAD)
					.setParameter(MaeEntidad.P_ENTIDAD, entidadI.getNombreEntidad());
			}
			
			TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.NQ_BY_FILTERS, MaeEntidad.class);
			MaeEntidad entidad = query.getResultStream().findFirst().orElse(new MaeEntidad());
			
			if(UtilsProject.isNullOrEmpty(entidad.getDocumento())) {
				
				//entidad.setNEntidad(entidadI.getId());
				entidad.setNombre(entidadI.getNombreEntidad());
				entidad.setDocumento(entidadI.getNumeroDocumento());
				entidad.setActivo(ConstantesProject.ACTIVO_ESTADO);
				
				this.sf.getCurrentSession().save(entidad);
			}
		}catch(Exception e) {
			logger.error("{} Error dao crearEntidad: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
	}

	@Override
	public void actualizarEntidad(String cuo, EntidadDTO entidadU) throws Exception {
		//obtener datos actuales
		
		try {
		
			if(!UtilsProject.isNullOrEmpty(entidadU.getId())) {
				this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ID)
					.setParameter(MaeEntidad.P_ID, entidadU.getId());
			}
		
		TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.NQ_BY_FILTERS, MaeEntidad.class);
		MaeEntidad entidad = query.getSingleResult();
		
		if(!UtilsProject.isNullOrEmpty(entidad.getNEntidad())) {
			entidad.setNombre(entidadU.getNombreEntidad());
			entidad.setDocumento(entidadU.getNumeroDocumento());
			entidad.setActivo(entidadU.getActivo());
			//entidad.setActivo((entidadU.getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO) || entidadU.getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_DESCRICION))   ? ConstantesProject.ACTIVO_ESTADO : ConstantesProject.INACTIVO_ESTADO);
			this.sf.getCurrentSession().update(entidad);
			
			} else {
				logger.error("{} Error dao actualizarEntidad, no se encontró entidad: {}", cuo);
			}
		}catch(Exception e) {
			logger.error("{} Error dao actualizarEntidad: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		
	}
	
	
	@Override
	public ResponsePerfilesDTO obtenerPerfiles(String cuo) throws Exception {
		ResponsePerfilesDTO rspt = new ResponsePerfilesDTO();
		try {

			this.sf.getCurrentSession().enableFilter(MaePerfil.F_ACTIVO).setParameter(MaePerfil.P_ACTIVO, ConstantesProject.ACTIVO_ESTADO);
			
			List<PerfilDTO> lista = new ArrayList<PerfilDTO>();
			TypedQuery<MaePerfil> query = this.sf.getCurrentSession().createNamedQuery(MaePerfil.NQ_BY_FILTERS, MaePerfil.class);
			query.getResultStream().forEach(perfil->{
				PerfilDTO perfilDTO = new PerfilDTO();
				perfilDTO.setId(Integer.toString(perfil.getNPerfil()));
				perfilDTO.setPerfil(perfil.getNombre());
				perfilDTO.setDescripcion(perfil.getDescripcion());
				perfilDTO.setRol(perfil.getCodigoRol());
				perfilDTO.setActivo(perfil.getActivo());
				//perfilDTO.setActivo(perfil.getActivo().equalsIgnoreCase(ConstantesProject.ACTIVO_ESTADO) ? ConstantesProject.ACTIVO_DESCRICION : ConstantesProject.INACTIVO_DESCRIPCION);
				lista.add(perfilDTO);
			});
			
			rspt.setIndicador(ConstantesProject.RPTA_1);
			rspt.setMensaje("Operación exitosa.");
			rspt.setPerfiles(lista);
			
		}catch(Exception e) {
			logger.error("{} Error dao obtenerPerfiles: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return rspt;
	}

}
