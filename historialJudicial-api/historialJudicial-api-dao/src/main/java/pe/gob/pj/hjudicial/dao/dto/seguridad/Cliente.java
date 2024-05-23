package pe.gob.pj.hjudicial.dao.dto.seguridad;

import java.io.Serializable;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cCodCliente;

	private String xNomCliente;

	public String getcCodCliente() {
		return cCodCliente;
	}

	public void setcCodCliente(String cCodCliente) {
		this.cCodCliente = cCodCliente;
	}

	public String getxNomCliente() {
		return xNomCliente;
	}

	public void setxNomCliente(String xNomCliente) {
		this.xNomCliente = xNomCliente;
	}

}
