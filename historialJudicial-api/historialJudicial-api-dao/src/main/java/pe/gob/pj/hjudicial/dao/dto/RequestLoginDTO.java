package pe.gob.pj.hjudicial.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestLoginDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "El usuario no puede ser nulo.")
	@NotBlank(message = "El usuario no puede ser vacío.")
	@Length(min = 4, max = 8, message = "El usuario tiene una longitud no válida [min=4,max=8].")
	@JsonProperty("usuario")
	private String usuario;
	
	@NotNull(message = "La contraseña de usuario no puede ser nulo.")
	@NotBlank(message = "La contraseña de usuario no puede ser vacío.")
	@Length(min = 3, max = 15, message = "La contraseña de usuario tiene una longitud no válida [min=5,max=15].")
	@JsonProperty("contrasenia")
	private String contrasenia;
	
	@NotNull(message = "Se requiere enviar Token anterior.")
	@NotBlank(message = "Se requiere enviar token anterior.")	
	private String token;
	
	//@NotNull(message = "El captcha no puede ser null")
	//@NotBlank(message = "El captcha iene un valor incorrecto")	
	private String tokenCaptcha;
		
	//@NotNull(message = "Indicador de captcha Habilitado no puede ser null")
	//@NotBlank(message = "Indicador de captcha Habilitado iene un valor incorrecto")	
	private String aplicaCaptcha; 
	
}
