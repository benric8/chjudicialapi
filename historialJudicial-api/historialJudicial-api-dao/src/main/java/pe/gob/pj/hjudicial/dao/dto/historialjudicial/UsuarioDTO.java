package pe.gob.pj.hjudicial.dao.dto.historialjudicial;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String P_USUARIO = "usuarioDTO";
	public static final String P_ID_USUARIO = "idUsuarioDTO";	

	private int id;
	private String entidad;
	private String usuario;
	private String contrasenia;
	private String perfil;
	private String numeroDocumento;
	private String apellidosNombres;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String activo;
	
	private String codigoRol;
	private List<OpcionDTO> opcions;

	private String token;
	
}