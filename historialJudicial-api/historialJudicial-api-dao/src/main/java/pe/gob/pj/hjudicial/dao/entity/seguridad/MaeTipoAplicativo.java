package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_tipo_aplicativo database table.
 * 
 */
@Entity
@Table(name="mae_tipo_aplicativo", schema = "seguridad")
@NamedQuery(name="MaeTipoAplicativo.findAll", query="SELECT m FROM MaeTipoAplicativo m")
public class MaeTipoAplicativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_tipo_aplicativo")
	private Integer nTipoAplicativo;

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

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	@Column(name="x_tipo_aplicativo")
	private String xTipoAplicativo;

	//bi-directional many-to-one association to MaeAplicativo
	@OneToMany(mappedBy="maeTipoAplicativo")
	private List<MaeAplicativo> maeAplicativos;

	public MaeTipoAplicativo() {
	}

	public Integer getNTipoAplicativo() {
		return this.nTipoAplicativo;
	}

	public void setNTipoAplicativo(Integer nTipoAplicativo) {
		this.nTipoAplicativo = nTipoAplicativo;
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

	public String getXTipoAplicativo() {
		return this.xTipoAplicativo;
	}

	public void setXTipoAplicativo(String xTipoAplicativo) {
		this.xTipoAplicativo = xTipoAplicativo;
	}

	public List<MaeAplicativo> getMaeAplicativos() {
		return this.maeAplicativos;
	}

	public void setMaeAplicativos(List<MaeAplicativo> maeAplicativos) {
		this.maeAplicativos = maeAplicativos;
	}

	public MaeAplicativo addMaeAplicativo(MaeAplicativo maeAplicativo) {
		getMaeAplicativos().add(maeAplicativo);
		maeAplicativo.setMaeTipoAplicativo(this);

		return maeAplicativo;
	}

	public MaeAplicativo removeMaeAplicativo(MaeAplicativo maeAplicativo) {
		getMaeAplicativos().remove(maeAplicativo);
		maeAplicativo.setMaeTipoAplicativo(null);

		return maeAplicativo;
	}

}