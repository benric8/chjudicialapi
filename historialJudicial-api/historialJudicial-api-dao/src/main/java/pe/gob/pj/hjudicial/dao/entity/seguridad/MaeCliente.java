package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_cliente database table.
 * 
 */
@Entity
@Table(name="mae_cliente", schema = "seguridad")
@NamedQuery(name="MaeCliente.findAll", query="SELECT m FROM MaeCliente m")
public class MaeCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_cliente")
	private Integer nCliente;

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

	@Column(name="c_cliente")
	private String cCliente;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="l_tipo_cliente")
	private String lTipoCliente;

	@Column(name="x_cliente")
	private String xCliente;

	@Column(name="x_descripcion")
	private String xDescripcion;

	//bi-directional many-to-one association to MaeUsuario
	@OneToMany(mappedBy="maeCliente")
	private List<MaeUsuario> maeUsuarios;

	public MaeCliente() {
	}

	public Integer getNCliente() {
		return this.nCliente;
	}

	public void setNCliente(Integer nCliente) {
		this.nCliente = nCliente;
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

	public String getCCliente() {
		return this.cCliente;
	}

	public void setCCliente(String cCliente) {
		this.cCliente = cCliente;
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

	public String getLTipoCliente() {
		return this.lTipoCliente;
	}

	public void setLTipoCliente(String lTipoCliente) {
		this.lTipoCliente = lTipoCliente;
	}

	public String getXCliente() {
		return this.xCliente;
	}

	public void setXCliente(String xCliente) {
		this.xCliente = xCliente;
	}

	public String getXDescripcion() {
		return this.xDescripcion;
	}

	public void setXDescripcion(String xDescripcion) {
		this.xDescripcion = xDescripcion;
	}

	public List<MaeUsuario> getMaeUsuarios() {
		return this.maeUsuarios;
	}

	public void setMaeUsuarios(List<MaeUsuario> maeUsuarios) {
		this.maeUsuarios = maeUsuarios;
	}

	public MaeUsuario addMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().add(maeUsuario);
		maeUsuario.setMaeCliente(this);

		return maeUsuario;
	}

	public MaeUsuario removeMaeUsuario(MaeUsuario maeUsuario) {
		getMaeUsuarios().remove(maeUsuario);
		maeUsuario.setMaeCliente(null);

		return maeUsuario;
	}

}