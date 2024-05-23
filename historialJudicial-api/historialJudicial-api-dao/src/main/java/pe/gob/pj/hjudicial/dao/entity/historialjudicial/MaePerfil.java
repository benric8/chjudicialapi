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
@Table(name = "MAE_PERFIL", schema = ConstantesProject.ESQUEMA_HJUDICIAL)
@NamedQueries(value = {
		@NamedQuery(name = MaePerfil.NQ_BY_FILTERS, query = "SELECT mp FROM MaePerfil mp"),
})
@FilterDefs(value = {
		@FilterDef(name = MaePerfil.F_PERFIL, parameters = {@ParamDef(name=MaePerfil.P_PERFIL, type = "string")}),
		@FilterDef(name = MaePerfil.F_ID, parameters = {@ParamDef(name=MaePerfil.P_ID, type = "integer")}),
		@FilterDef(name = MaePerfil.F_ACTIVO, parameters = {@ParamDef(name=MaePerfil.P_ACTIVO, type = "string")})
})
@Filters(value = {
		@Filter(name = MaePerfil.F_PERFIL, condition = "X_PERFIL=:parameterPerfil"),
		@Filter(name = MaePerfil.F_ID, condition = "N_PERFIL=:parameterId"),
		@Filter(name = MaePerfil.F_ACTIVO, condition = "L_ACTIVO=:parameterActivo")
})
public class MaePerfil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String NQ_BY_FILTERS = "MaePerfil.findByFilters";
	
	public static final String F_ID = "MaePerfil.filterId";
	public static final String F_PERFIL = "MaePerfil.filterPerfil";
	public static final String F_ACTIVO = "MaePerfil.filterActivo";
	
	public static final String P_ID = "parameterId";
	public static final String P_PERFIL = "parameterPerfil";
	public static final String P_ACTIVO = "parameterActivo";	
	
	@SequenceGenerator(name = "GENERATOR_MAE_PERFIL", schema = ConstantesProject.ESQUEMA_HJUDICIAL, sequenceName = "MAE_PERFIL_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_PERFIL", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_PERFIL")
	private int nPerfil;
	
	@Column(name = "X_PERFIL")
	private String nombre;
	
	@Column(name = "X_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "X_ROL")
	private String codigoRol;
	
	@Column(name = "L_ACTIVO")
	private String activo;

}
