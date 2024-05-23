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
@Table(name = "MAE_USUARIO", schema = ConstantesProject.ESQUEMA_HJUDICIAL)
@NamedQueries(value = {
		@NamedQuery(name = MaeUsuario.NQ_BY_FILTERS, query = "SELECT mu FROM MaeUsuario mu"),
		@NamedQuery(name = MaeUsuario.NQ_UPDATE_CLAVE, query = "UPDATE MaeUsuario SET clave=:parameterClave WHERE nUsuario=:parameterId")
})
@FilterDefs(value = {
		@FilterDef(name = MaeUsuario.F_USUARIO, parameters = {@ParamDef(name=MaeUsuario.P_USUARIO, type = "string")}),
		@FilterDef(name = MaeUsuario.F_ID, parameters = {@ParamDef(name=MaeUsuario.P_ID, type = "integer")}),
		@FilterDef(name = MaeUsuario.F_CLAVE, parameters = {@ParamDef(name=MaeUsuario.P_CLAVE, type = "string")}),
		@FilterDef(name = MaeUsuario.F_ACTIVO, parameters = {@ParamDef(name=MaeUsuario.P_ACTIVO, type = "string")})
})
@Filters(value = {
		@Filter(name = MaeUsuario.F_USUARIO, condition = "X_USUARIO=:parameterUsuario"),
		@Filter(name = MaeUsuario.F_ID, condition = "N_USUARIO=:parameterId"),
		@Filter(name = MaeUsuario.F_CLAVE, condition = "X_PASSWORD=:parameterClave"),
		@Filter(name = MaeUsuario.F_ACTIVO, condition = "L_ACTIVO=:parameterActivo")
})
public class MaeUsuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String NQ_BY_FILTERS = "MaeUsuario.findByFilters";
	public static final String NQ_UPDATE_CLAVE = "MaeUsuario.updateClave";
	
	public static final String F_ID = "MaeUsuario.filterId";
	public static final String F_USUARIO = "MaeUsuario.filterUsuario";
	public static final String F_CLAVE = "MaeUsuario.filterClave";
	public static final String F_ACTIVO = "MaeUsuario.filterActivo";
	
	public static final String P_ID = "parameterId";
	public static final String P_USUARIO = "parameterUsuario";
	public static final String P_CLAVE = "parameterClave";
	public static final String P_ACTIVO = "parameterActivo";

	@SequenceGenerator(name = "GENERATOR_MAE_USUARIO", schema = ConstantesProject.ESQUEMA_HJUDICIAL, sequenceName = "MAE_USUARIO_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_USUARIO", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_USUARIO", nullable = false)
	private int nUsuario;
	
	@Column(name = "X_USUARIO", nullable = false)
	private String usuario;
	
	@Column(name = "X_PASSWORD", nullable = false)
	private String clave;
	
	@Column(name = "X_NOMBRE", nullable = false)
	private String nombres;
	
	@Column(name = "X_DOCUMENTO")
	private String numeroDocumento;

	@Column(name = "X_APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column(name = "X_APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name = "X_CORREO")
	private String correo;
	
	@Column(name = "L_ACTIVO")
	private String activo;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ENTIDAD")
	private MaeEntidad entidad;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_PERFIL")
	private MaePerfil perfil;
	
	
}
