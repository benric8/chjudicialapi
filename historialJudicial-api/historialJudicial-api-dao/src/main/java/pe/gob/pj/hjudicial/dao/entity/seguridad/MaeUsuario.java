package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_usuario database table.
 * 
 */
@Entity
@Table(name="mae_usuario", schema = "seguridad")
@NamedQuery(name="MaeUsuario.findAll", query="SELECT m FROM MaeUsuario m")
@NamedQuery(name=MaeUsuario.FIND_BY_ID, query="SELECT m FROM MaeUsuario m WHERE m.lActivo = '1' AND m.nUsuario = :idUsuario ")
@NamedQuery(name=MaeUsuario.FIND_BY_CODIGO, query="SELECT m FROM MaeUsuario m WHERE m.lActivo = '1' AND m.cUsuario = :codigo ")
public class MaeUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_ID = "MaeUsuario.buscarPorId";
	public static final String P_N_USUARIO = "idUsuario";
	public static final String FIND_BY_CODIGO = "MaeUsuario.buscarPorcodigo";
	public static final String P_N_CODIGO = "codigo";

	@Id
	@Column(name="n_usuario")
	private Integer nUsuario;

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

	@Column(name="c_clave")
	private String cClave;

	@Column(name="c_usuario")
	private String cUsuario;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="f_registro")
	private Timestamp fRegistro;

	@Column(name="l_activo")
	private String lActivo;

	//bi-directional many-to-one association to MaeRolUsuario
	@OneToMany(mappedBy="maeUsuario")
	private List<MaeRolUsuario> maeRolUsuarios;

	//bi-directional many-to-one association to MaeCliente
	@ManyToOne
	@JoinColumn(name="n_cliente")
	private MaeCliente maeCliente;

	public MaeUsuario() {
	}

	public Integer getNUsuario() {
		return this.nUsuario;
	}

	public void setNUsuario(Integer nUsuario) {
		this.nUsuario = nUsuario;
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

	public String getCClave() {
		return this.cClave;
	}

	public void setCClave(String cClave) {
		this.cClave = cClave;
	}

	public String getCUsuario() {
		return this.cUsuario;
	}

	public void setCUsuario(String cUsuario) {
		this.cUsuario = cUsuario;
	}

	public Timestamp getFAud() {
		return this.fAud;
	}

	public void setFAud(Timestamp fAud) {
		this.fAud = fAud;
	}

	public Timestamp getFRegistro() {
		return this.fRegistro;
	}

	public void setFRegistro(Timestamp fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getLActivo() {
		return this.lActivo;
	}

	public void setLActivo(String lActivo) {
		this.lActivo = lActivo;
	}

	public List<MaeRolUsuario> getMaeRolUsuarios() {
		return this.maeRolUsuarios;
	}

	public void setMaeRolUsuarios(List<MaeRolUsuario> maeRolUsuarios) {
		this.maeRolUsuarios = maeRolUsuarios;
	}

	public MaeRolUsuario addMaeRolUsuario(MaeRolUsuario maeRolUsuario) {
		getMaeRolUsuarios().add(maeRolUsuario);
		maeRolUsuario.setMaeUsuario(this);

		return maeRolUsuario;
	}

	public MaeRolUsuario removeMaeRolUsuario(MaeRolUsuario maeRolUsuario) {
		getMaeRolUsuarios().remove(maeRolUsuario);
		maeRolUsuario.setMaeUsuario(null);

		return maeRolUsuario;
	}

	public MaeCliente getMaeCliente() {
		return this.maeCliente;
	}

	public void setMaeCliente(MaeCliente maeCliente) {
		this.maeCliente = maeCliente;
	}

}