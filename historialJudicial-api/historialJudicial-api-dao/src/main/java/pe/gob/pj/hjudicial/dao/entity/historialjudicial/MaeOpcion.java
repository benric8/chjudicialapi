package pe.gob.pj.hjudicial.dao.entity.historialjudicial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "MAE_OPCION", schema = ConstantesProject.ESQUEMA_HJUDICIAL)
public class MaeOpcion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name = "GENERATOR_MAE_OPCION", schema = ConstantesProject.ESQUEMA_HJUDICIAL, sequenceName = "MAE_OPCION_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_OPCION", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_OPCION")
	private int nOpcion;

	@Column(name = "X_OPCION")
	private String nombre;

	@Column(name = "X_URL")
	private String url;

	@Column(name = "X_ICONO")
	private String icono;

	@Column(name = "L_ACTIVO")
	private String activo;
	
}
