package pe.gob.pj.hjudicial.dao.entity.historialjudicial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "MAE_ENTIDAD", schema = ConstantesProject.ESQUEMA_HJUDICIAL)
@NamedQueries(value = {
		@NamedQuery(name = MaeEntidad.NQ_BY_FILTERS, query = "SELECT me FROM MaeEntidad me"),
})
@FilterDefs(value = {
		@FilterDef(name = MaeEntidad.F_ENTIDAD, parameters = {@ParamDef(name=MaeEntidad.P_ENTIDAD, type = "string")}),
		@FilterDef(name = MaeEntidad.F_ID, parameters = {@ParamDef(name=MaeEntidad.P_ID, type = "integer")}),
		@FilterDef(name = MaeEntidad.F_ACTIVO, parameters = {@ParamDef(name=MaeEntidad.P_ACTIVO, type = "string")})
})
@Filters(value = {
		@Filter(name = MaeEntidad.F_ENTIDAD, condition = "X_ENTIDAD=:parameterEntidad"),
		@Filter(name = MaeEntidad.F_ID, condition = "N_ENTIDAD=:parameterId"),
		@Filter(name = MaeEntidad.F_ACTIVO, condition = "L_ACTIVO=:parameterActivo")
})
public class MaeEntidad extends Auditoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String NQ_BY_FILTERS = "MaeEntidad.findByFilters";
	
	public static final String F_ID = "MaeEntidad.filterId";
	public static final String F_ENTIDAD = "MaeEntidad.filterEntidad";
	public static final String F_ACTIVO = "MaeEntidad.filterActivo";
	
	public static final String P_ID = "parameterId";
	public static final String P_ENTIDAD = "parameterEntidad";
	public static final String P_ACTIVO = "parameterActivo";	
	
	@SequenceGenerator(name = "GENERATOR_MAE_ENTIDAD", schema = ConstantesProject.ESQUEMA_HJUDICIAL, sequenceName = "MAE_ENTIDAD_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_ENTIDAD", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_ENTIDAD", nullable = false)
	private int nEntidad;
	
	@Column(name = "X_ENTIDAD", nullable = false)
	private String nombre;
	
	@Column(name = "X_DOCUMENTO", nullable = false)
	private String documento;
	
	@Column(name = "L_ACTIVO", nullable = false)
	private String activo;

}
