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
public class RequestUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	@NotNull(message = "El parámetro usuario no puede ser nulo.")
	@NotBlank(message = "El parámetro usuario no puede ser vacío.")	
	@JsonProperty("usuario")
	private String usuario;
	
	@Length(min = 1, message = "El parámetro contrasenia tiene un tamaño no valido (min=1).")
	@NotBlank(message = "El parámetro contrasenia no puede ser vacio.")
	@NotNull(message = "El parámetro contrasenia no puede ser nulo.")
	@JsonProperty("contrasenia")	
	private String contrasenia;
	
	@NotNull(message = "El parámetro entidad no puede ser nulo.")
	@NotBlank(message = "El parámetro entidad no puede ser vacío.")	
	@JsonProperty("entidad")	
	private String entidad;
	
	@Pattern(regexp = ConstantesProject.PATTERN_NUMBER, message = "El parámetro perfil tiene un formato no valido.")
	@NotNull(message = "El perfil no puede ser nulo.")
	@NotBlank(message = "El perfil no puede ser vacío.")	
	@JsonProperty("perfil")	
	private String perfil;
	
	@Pattern(regexp = ConstantesProject.PATTERN_NUMBER, message = "El parámetro numeroDocumento tiene un formato no valido.")	
	@NotNull(message = "El numero documento de entidad no puede ser nulo.")
	@NotBlank(message = "El numero documento de entidad no puede ser vacío.")
	@Length(min = 8, max = 20, message = "El nombre de entidad tiene una longitud no válida [min=8,max=20].")
	@JsonProperty("numeroDocumento")
	private String numeroDocumento;
	
	@Length(min = 2, max = 60, message = "El parámetro nombres tiene un tamaño no valido (min=2,max=60).")
	@NotBlank(message = "El parámetro nombres no puede ser vacio.")
	@NotNull(message = "El parámetro nombres no puede ser nulo.")
	@JsonProperty("nombres")
	private String nombres;
	
	@Length(min = 2, max = 60, message = "El parámetro apellidoPaterno tiene un tamaño no valido (min=2,max=60).")
	@JsonProperty("apellidoPaterno")	
	private String apellidoPaterno;
	
	@Length(min = 2, max = 60, message = "El parámetro apellidoMaterno tiene un tamaño no valido (min=2,max=60).")
	@JsonProperty("apellidoMaterno")	
	private String apellidoMaterno;
	
	@Pattern(regexp = ConstantesProject.PATTERN_EMAIL, message = "El parámetro correo tiene un formato no valido.")
	@Length(min = 3, max = 50, message = "El parámetro correo tiene un tamaño no valido (min=3,max=50).")
	@NotBlank(message = "El parámetro correo no puede ser vacio.")
	@NotNull(message = "El parámetro correo no puede ser nulo.")
	@JsonProperty("correo")
	private String correo;
	
	@NotNull(message = "El parámetro activo no puede ser nulo.")
	@NotBlank(message = "El parámetro activo no puede ser vacío.")	
	@JsonProperty("activo")		
	private String activo;

	
}
