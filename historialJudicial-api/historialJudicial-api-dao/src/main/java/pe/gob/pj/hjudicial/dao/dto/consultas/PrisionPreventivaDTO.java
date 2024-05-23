/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.dto.consultas;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * <pre>
 * Objeto     : PrisionPreventivaDTO.
 * Descripción: Clase POJO para manejar los datos de Prisión Preventiva.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Data
public class PrisionPreventivaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoParte;
	private String dni;
	private String nombres;
	private String delito;
	private String medida;
	private String fechaInicio;
	private String fechaFin;
	//private String tipoIncidente;
	//private String fechaInicioMedCoercitiva;
	//private String fechaFinMedCoercitiva;
	private String estado;

	private Long nUnico;
	private int nIncidente;
	private String expediente;
	private String resDictaPrisionPreventiva;
	private String fechaDictaPrisionPreventina;
	private String medidaCoercitiva;

}
