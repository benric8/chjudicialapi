package pe.gob.pj.hjudicial.dao.entity.historialjudicial;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MAE_OPCION_PERFIL", schema = ConstantesProject.ESQUEMA_HJUDICIAL)
@NamedQueries(value = {
		@NamedQuery(name = MaeOpcionPerfil.NQ_FILTERS, query = "SELECT mop FROM MaeOpcionPerfil mop")
})
@FilterDefs(value = {
		@FilterDef(name = MaeOpcionPerfil.F_ID, parameters = {@ParamDef(name=MaeOpcionPerfil.P_ID, type = "int")}),
		@FilterDef(name = MaeOpcionPerfil.F_IDPERFIL, parameters = {@ParamDef(name=MaeOpcionPerfil.P_IDPERFIL, type = "int")}),
		@FilterDef(name = MaeOpcionPerfil.F_ACTIVO, parameters = {@ParamDef(name=MaeOpcionPerfil.P_ACTIVO, type = "string")})
})
@Filters(value = {
		@Filter(name = MaeOpcionPerfil.F_ID, condition = "N_OPCION_PERFIL=:parameterId"),
		@Filter(name = MaeOpcionPerfil.F_IDPERFIL, condition = "N_PERFIL=:parameterIdPerfil"),
		@Filter(name = MaeOpcionPerfil.F_ACTIVO, condition = "L_ACTIVO=:parameterActivo")
})
public class MaeOpcionPerfil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String NQ_FILTERS = "MaeOpcionPerfil.findByFilters";
	
	public static final String F_ID = "MaeOpcionPerfil.filterIdOpcionPerfil";
	public static final String F_IDPERFIL = "MaeOpcionPerfil.filterIdPerfil";
	public static final String F_ACTIVO = "MaeOpcionPerfil.filterActivo";
	
	public static final String P_ID = "parameterId";
	public static final String P_IDPERFIL = "parameterIdPerfil";
	public static final String P_ACTIVO = "parameterActivo";

	@SequenceGenerator(name = "GENERATOR_MAE_OPCION_PERFIL", schema = ConstantesProject.ESQUEMA_HJUDICIAL, sequenceName = "MAE_OPCION_PERFIL_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_OPCION_PERFIL", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_OPCION_PERFIL")
	private int nOpcionPerfil;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_OPCION")
	private MaeOpcion opcion;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_PERFIL")
	private MaePerfil perfil;
	
	@Column(name = "L_ACTIVO")
	private String activo;

}
