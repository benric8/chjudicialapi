package pe.gob.pj.hjudicial.dao.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mae_operacion database table.
 * 
 */
@Entity
@Table(name="mae_operacion", schema = "seguridad")
@NamedQuery(name="MaeOperacion.findAll", query="SELECT m FROM MaeOperacion m")
public class MaeOperacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_operacion")
	private Integer nOperacion;

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

	@Column(name="x_endpoint")
	private String xEndpoint;

	@Column(name="x_operacion")
	private String xOperacion;

	//bi-directional many-to-one association to MaeAplicativo
	@ManyToOne
	@JoinColumn(name="n_aplicativo")
	private MaeAplicativo maeAplicativo;

	//bi-directional many-to-one association to MaeRol
	@ManyToOne
	@JoinColumn(name="n_rol")
	private MaeRol maeRol;

	public MaeOperacion() {
	}

	public Integer getNOperacion() {
		return this.nOperacion;
	}

	public void setNOperacion(Integer nOperacion) {
		this.nOperacion = nOperacion;
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

	public String getXEndpoint() {
		return this.xEndpoint;
	}

	public void setXEndpoint(String xEndpoint) {
		this.xEndpoint = xEndpoint;
	}

	public String getXOperacion() {
		return this.xOperacion;
	}

	public void setXOperacion(String xOperacion) {
		this.xOperacion = xOperacion;
	}

	public MaeAplicativo getMaeAplicativo() {
		return this.maeAplicativo;
	}

	public void setMaeAplicativo(MaeAplicativo maeAplicativo) {
		this.maeAplicativo = maeAplicativo;
	}

	public MaeRol getMaeRol() {
		return this.maeRol;
	}

	public void setMaeRol(MaeRol maeRol) {
		this.maeRol = maeRol;
	}

}