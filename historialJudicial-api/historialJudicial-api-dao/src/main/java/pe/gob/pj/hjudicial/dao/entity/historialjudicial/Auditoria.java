package pe.gob.pj.hjudicial.dao.entity.historialjudicial;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public abstract class Auditoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "F_AUD")
	private Date fAud;
	
	@Column(name = "B_AUD")
	private String bAud;
	
	@Column(name = "C_AUD_UID")
	private String uidAud;
	
	@Column(name = "C_AUD_UIDRED")
	private String uiredAud;
	
	@Column(name = "C_AUD_PC")
	private String pcAud;
	
	@Column(name = "C_AUD_IP")
	private String ipAud;
	
	@Column(name = "C_AUD_MCADDR")
	private String macAud;
	
}
