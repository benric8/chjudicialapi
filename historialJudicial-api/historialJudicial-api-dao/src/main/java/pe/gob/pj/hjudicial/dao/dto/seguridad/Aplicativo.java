package pe.gob.pj.hjudicial.dao.dto.seguridad;

import java.io.Serializable;

public class Aplicativo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cCodAplicativo;

	private String xNomAplicativo;

	public String getcCodAplicativo() {
		return cCodAplicativo;
	}

	public void setcCodAplicativo(String cCodAplicativo) {
		this.cCodAplicativo = cCodAplicativo;
	}

	public String getxNomAplicativo() {
		return xNomAplicativo;
	}

	public void setxNomAplicativo(String xNomAplicativo) {
		this.xNomAplicativo = xNomAplicativo;
	}

}
