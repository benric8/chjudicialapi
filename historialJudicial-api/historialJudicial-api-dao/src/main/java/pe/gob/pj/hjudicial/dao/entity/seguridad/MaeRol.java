package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_rol database table.
 * 
 */
@Entity
@Table(name="mae_rol", schema = "seguridad")
@NamedQuery(name="MaeRol.findAll", query="SELECT m FROM MaeRol m")
@NamedQuery(name=MaeRol.FIND_ROLES_BY_ID_USUARIO, query="SELECT m FROM MaeRol m JOIN m.maeRolUsuarios ur WHERE m.lActivo = '1' AND ur.maeUsuario.nUsuario = :idUsuario")
public class MaeRol implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ROLES_BY_ID_USUARIO = "MaeRol.rolesPorUsuario";

	@Id
	@Column(name="n_rol")
	private Integer nRol;

	@Column(name="b_aud")
	private String bAud;

	@Column(name="c_aud_ip")
	private String cAudIp;

	@Column(name="c_aud_mcaddr")
	private String cAudMcaddr;

	@Column(name="c_aud_pc")
	private String cAudPc;

	@Column(name="c_aud_uid")
	private String cAudUid;

	@Column(name="c_aud_uidred")
	private String cAudUidred;

	@Column(name="c_rol")
	private String cRol;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	@Column(name="x_rol")
	private String xRol;

	//bi-directional many-to-one association to MaeOperacion
	@OneToMany(mappedBy="maeRol")
	private List<MaeOperacion> maeOperacions;

	//bi-directional many-to-one association to MaeRolUsuario
	@OneToMany(mappedBy="maeRol")
	private List<MaeRolUsuario> maeRolUsuarios;

	public MaeRol() {
	}

	public Integer getNRol() {
		return this.nRol;
	}

	public void setNRol(Integer nRol) {
		this.nRol = nRol;
	}

	public String getBAud() {
		return this.bAud;
	}

	public void setBAud(String bAud) {
		this.bAud = bAud;
	}

	public String getCAudIp() {
		return this.cAudIp;
	}

	public void setCAudIp(String cAudIp) {
		this.cAudIp = cAudIp;
	}

	public String getCAudMcaddr() {
		return this.cAudMcaddr;
	}

	public void setCAudMcaddr(String cAudMcaddr) {
		this.cAudMcaddr = cAudMcaddr;
	}

	public String getCAudPc() {
		return this.cAudPc;
	}

	public void setCAudPc(String cAudPc) {
		this.cAudPc = cAudPc;
	}

	public String getCAudUid() {
		return this.cAudUid;
	}

	public void setCAudUid(String cAudUid) {
		this.cAudUid = cAudUid;
	}

	public String getCAudUidred() {
		return this.cAudUidred;
	}

	public void setCAudUidred(String cAudUidred) {
		this.cAudUidred = cAudUidred;
	}

	public String getCRol() {
		return this.cRol;
	}

	public void setCRol(String cRol) {
		this.cRol = cRol;
	}

	public Timestamp getFAud() {
		return this.fAud;
	}

	public void setFAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public String getLActivo() {
		return this.lActivo;
	}

	public void setLActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public String getXDescripcion() {
		return this.xDescripcion;
	}

	public void setXDescripcion(String xDescripcion) {
		this.xDescripcion = xDescripcion;
	}

	public String getXRol() {
		return this.xRol;
	}

	public void setXRol(String xRol) {
		this.xRol = xRol;
	}

	public List<MaeOperacion> getMaeOperacions() {
		return this.maeOperacions;
	}

	public void setMaeOperacions(List<MaeOperacion> maeOperacions) {
		this.maeOperacions = maeOperacions;
	}

	public MaeOperacion addMaeOperacion(MaeOperacion maeOperacion) {
		getMaeOperacions().add(maeOperacion);
		maeOperacion.setMaeRol(this);

		return maeOperacion;
	}

	public MaeOperacion removeMaeOperacion(MaeOperacion maeOperacion) {
		getMaeOperacions().remove(maeOperacion);
		maeOperacion.setMaeRol(null);

		return maeOperacion;
	}

	public List<MaeRolUsuario> getMaeRolUsuarios() {
		return this.maeRolUsuarios;
	}

	public void setMaeRolUsuarios(List<MaeRolUsuario> maeRolUsuarios) {
		this.maeRolUsuarios = maeRolUsuarios;
	}

	public MaeRolUsuario addMaeRolUsuario(MaeRolUsuario maeRolUsuario) {
		getMaeRolUsuarios().add(maeRolUsuario);
		maeRolUsuario.setMaeRol(this);

		return maeRolUsuario;
	}

	public MaeRolUsuario removeMaeRolUsuario(MaeRolUsuario maeRolUsuario) {
		getMaeRolUsuarios().remove(maeRolUsuario);
		maeRolUsuario.setMaeRol(null);

		return maeRolUsuario;
	}

}