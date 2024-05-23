package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;

@Data
public class RequestEntidadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	@NotNull(message = "El nombre de entidad no puede ser nulo.")
	@NotBlank(message = "El nombre de entidad no puede ser vacío.")
	@Length(min = 1, max = 100, message = "El nombre de entidad tiene una longitud no válida [min=1,max=100].")
	@JsonProperty("nombreEntidad")
	private String nombreEntidad;
	
	@Pattern(regexp = ConstantesProject.PATTERN_NUMBER, message = "El parámetro numeroDocumento tiene un formato no valido.")	
	@NotNull(message = "El numero documento de entidad no puede ser nulo.")
	@NotBlank(message = "El numero documento de entidad no puede ser vacío.")
	@Length(min = 8, max = 20, message = "El nombre de entidad tiene una longitud no válida [min=8,max=20].")
	@JsonProperty("numeroDocumento")
	private String numeroDocumento;
	
	@NotNull(message = "El parámetro activo no puede ser nulo.")
	@NotBlank(message = "El parámetro activo no puede ser vacío.")	
	@JsonProperty("activo")
	private String activo;
}
