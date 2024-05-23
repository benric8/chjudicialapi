package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_aplicativo database table.
 * 
 */
@Entity
@Table(name="mae_aplicativo", schema = "seguridad")
@NamedQuery(name="MaeAplicativo.findAll", query="SELECT m FROM MaeAplicativo m")
public class MaeAplicativo implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
	@Column(name="n_aplicativo")
	private Integer nAplicativo;

	@Column(name="b_aud")
	private String bAud;

	@Column(name="c_aplicativo")
	private String cAplicativo;

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

	@Column(name="x_aplicativo")
	private String xAplicativo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	//bi-directional many-to-one association to MaeTipoAplicativo
	@ManyToOne
	@JoinColumn(name="n_tipo_aplicativo")
	private MaeTipoAplicativo maeTipoAplicativo;

	//bi-directional many-to-one association to MaeOperacion
	@OneToMany(mappedBy="maeAplicativo")
	private List<MaeOperacion> maeOperacions;

	public MaeAplicativo() {
	}

	public Integer getNAplicativo() {
		return this.nAplicativo;
	}

	public void setNAplicativo(Integer nAplicativo) {
		this.nAplicativo = nAplicativo;
	}

	public String getBAud() {
		return this.bAud;
	}

	public void setBAud(String bAud) {
		this.bAud = bAud;
	}

	public String getCAplicativo() {
		return this.cAplicativo;
	}

	public void setCAplicativo(String cAplicativo) {
		this.cAplicativo = cAplicativo;
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

	public String getXAplicativo() {
		return this.xAplicativo;
	}

	public void setXAplicativo(String xAplicativo) {
		this.xAplicativo = xAplicativo;
	}

	public String getXDescripcion() {
		return this.xDescripcion;
	}

	public void setXDescripcion(String xDescripcion) {
		this.xDescripcion = xDescripcion;
	}

	public MaeTipoAplicativo getMaeTipoAplicativo() {
		return this.maeTipoAplicativo;
	}

	public void setMaeTipoAplicativo(MaeTipoAplicativo maeTipoAplicativo) {
		this.maeTipoAplicativo = maeTipoAplicativo;
	}

	public List<MaeOperacion> getMaeOperacions() {
		return this.maeOperacions;
	}

	public void setMaeOperacions(List<MaeOperacion> maeOperacions) {
		this.maeOperacions = maeOperacions;
	}

	public MaeOperacion addMaeOperacion(MaeOperacion maeOperacion) {
		getMaeOperacions().add(maeOperacion);
		maeOperacion.setMaeAplicativo(this);

		return maeOperacion;
	}

	public MaeOperacion removeMaeOperacion(MaeOperacion maeOperacion) {
		getMaeOperacions().remove(maeOperacion);
		maeOperacion.setMaeAplicativo(null);

		return maeOperacion;
	}

}