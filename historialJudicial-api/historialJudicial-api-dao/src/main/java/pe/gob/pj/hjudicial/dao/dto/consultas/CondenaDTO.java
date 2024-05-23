package pe.gob.pj.hjudicial.dao.dto.consultas;

import java.io.Serializable;

import lombok.Data;

@Data
public class CondenaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// numeroIdentSol
	private String documento;
	//	apellidoPaternoSol
	//	apellidoMaternoSol
	//	nombresSol
	private String nombresApellidos;
	// ?
	private String pena;
	// delito
	private String delito;
	// annoPena
	// mesPena
	// diasPena
	private String tiempoCondena;
	//fechaPronunBoletn
	private String fechaSentencia;
	//fechaPronunBoletn
	private String fechaInicio;
	// fechaFinPenaComp
	private String fechaFin;
	// rehabilitado
	private String rehabilitado;

	private Long boletin;
	private int idTipoBoletin;
	private String numeroArticulo;
	private Long jornadas;
	private Long diasMulta;
	private int diasAcumulado;
	private String tipoBoletin;

	public CondenaDTO(String documento, String nombresApellidos, String pena, String delito,
			String tiempoCondena, String fechaSentencia, String fechaInicio, String fechaFin, String rehabilitado,
			Long boletin, int idTipoBoletin, String numeroArticulo, Long jornadas, Long diasMulta, int diasAcumulado, String tipoBoletin) {
		super();
		this.documento = documento;
		this.nombresApellidos = nombresApellidos;
		this.pena = pena;
		this.delito = delito;
		this.tiempoCondena = tiempoCondena;
		this.fechaSentencia = fechaSentencia;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.rehabilitado = rehabilitado;
		this.boletin = boletin;
		this.idTipoBoletin = idTipoBoletin;
		this.numeroArticulo = numeroArticulo;
		this.jornadas = jornadas;
		this.diasMulta = diasMulta;
		this.diasAcumulado = diasAcumulado;
		this.tipoBoletin = tipoBoletin;
	}

	public CondenaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
