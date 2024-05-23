package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.dto.consultas.CondenaDTO;

@Data
public class ResponseConsultaCondenasDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String indicador;
	private String mensaje;
	private List<CondenaDTO> condenas;
	
	public ResponseConsultaCondenasDTO(String indicador, String mensaje, List<CondenaDTO> condenas) {
		super();
		this.indicador = indicador;
		this.mensaje = mensaje;
		this.condenas = condenas;
	}

	public ResponseConsultaCondenasDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
