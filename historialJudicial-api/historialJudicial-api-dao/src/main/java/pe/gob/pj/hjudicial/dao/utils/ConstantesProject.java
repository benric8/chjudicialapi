package pe.gob.pj.hjudicial.dao.utils;

public class ConstantesProject {

	public static final int DEFAULT_PAGINATION_PAGE_SIZE = 10;
	
	public static final int TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS = 300;
	public static final int TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS = 600;
	public static final int NRO_VECES_REFRESH_CON_TOKEN_EXPIRADO = 100;
	
	public static final String VERSION = "1.0.0";
	public static final String SHORT_NAME_APP = "HJUDICIAL";

	public static final String ESQUEMA_SEGURIDAD = "seguridad";
	public static final String ESQUEMA_HJUDICIAL = "historialjudicial";

	public final static String PATTERN_NUMBER = "[0-9]+";
	public static final String PATTERN_IP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String PATTERN_MAC = "([0-9A-F]{2}[:-]){5}([0-9A-F]{2})";
	public static final String PATTERN_ALPHANUMBER = "[a-zA-Z0-9]+";
	public static final String PATTERN_1_0 = "[10]";
	public static final String PATTERN_S_N = "[SN]";
	public static final String PATTERN_FORMATO_EXPEDIENTE = "(\\d{5})[-](\\d{4})[-](\\d{1,4})[-](\\d{4})[-]([A-Za-z]{2})[-]([A-Za-z]{2})[-](\\d{2})";
	public static final String PATTERN_FECHA = "([0][1-9]|[1-2][0-9]|[3][0-1])/([0][1-9]|[1][0-2])/(\\d{4})";
	public static final String PATTERN_FECHA_DD_MM_YYYY_HH_MM_SS_SSS = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})";
	public static final String PATTERN_EMAIL = "([A-Za-z0-9]+[._-]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[-]?[A-Za-z0-9]+\\.)*[A-Za-z0-9]{2,6}";

	public static final String PATTERN_FECHA_YYYYMMDD = "yyyyMMdd";
	public static final String PATTERN_FECHA_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String PATTERN_FECHA_YYYY_MM_DD_ = "yyyy-MM-dd";
	public static final String PATTERN_FECHA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String PATTERN_FECHA_DD_MM_YYYY_HH_MM = "dd/MM/yyyy hh:mm a";

	public static final String SQL_ACCION_INSERT = "I";
	public static final String SQL_ACCION_UPDATE = "U";
	
	public static final String LETRA_S = "S";

	public static final String ACTIVO_DESCRICION = "Activo";
	public static final String ACTIVO_ESTADO = "1";
	
	public static final String INACTIVO_DESCRIPCION = "Inactivo";
	public static final String INACTIVO_ESTADO = "0";

	public static final String RPTA_0 = "0";
	public static final String RPTA_1 = "1";
	
	public static final String TIPO_BUSQUEDA_CONDENAS_DNI = "D";
	public static final String TIPO_BUSQUEDA_CONDENAS_NOMBRES = "N";
	public static final String TIPO_BUSQUEDA_PPREVENTIVA_DNI = "D";
	public static final String TIPO_BUSQUEDA_PPREVENTIVA_NOMBRES = "N";

	public static final String AUD_CUO = "cuo";
	public static final String REMOTE_IP = "ipRemota";
	public static final String TOKEN_ACCESO_EXTERNO = "out";
	public static final String TOKEN_ACCESO_INTERNO = "in";

	public static final String CLAIM_ROL = "rol";
	public static final String CLAIM_USUARIO = "usuario";
	public static final String CLAIM_IP = "remoteIp";
	public static final String CLAIM_NUMERO = "numero";
	public static final String CLAIM_ACCESO = "log_";
	public static final String CLAIM_LIMIT= "limit";
	
	public static final String METHOD_CORTA_ULTIMA_BARRA_INVERTIDA = "PUT";

	public static final String C_500 = "500";
	public static final String C_404 = "404";
	public static final String C_405 = "405";
	public static final String C_200 = "200";
	public static final String C_400 = "400";
	public static final String C_401 = "401";
	public static final String C_403 = "403";
	
	public static final String C_E01 = "E01";
	public static final String C_E02 = "E02";
	public static final String C_E03 = "E03";
	
	public static final String X_E01 = "Los sentimos, la sesión ha expirado.";
	public static final String X_E02 = "Lo sentimos, hubo un problema al validar el token.";
	public static final String X_E03 = "El usuario ya se encuentra registrado ";
	
	public static final String C_EXITO   = "0000";
	public static final String X_EXITO = "La operación se realizó de manera exitosa.";
	
	public static final String USUARIO_CONSULTA_DEFAULT = "uc_HistorialJudicial";
	
	public class Mensajes {
		public static final String MSG_ERROR_GENERICO_CONVERSION = "El tipo de dato de entrada es incorrecto";
	}

	public class Seguridad {
		public static final String SECRET_TOKEN = "configuracion.seguridad.secretToken";
		public static final String ID_APLICATIVO = "configuracion.seguridad.idaplicativo";
	}
	
	public class Captcha{
		public static final String CAPTCHA_TOKEN = "consulta.hjudicial.captcha.token";
		public static final String CAPTCHA_URL = "consulta.hjudicial.captcha.url";
	}

	public class Reniec {
		/* CODIGO TIPO DE CONSULTA PARA EL WEBSERVICE DE RENIEC */
		public static final String TIPO_CONSULTA_POR_NUMERO_DNI ="2";
		
		public static final String ENDPOINT = "configuracion.reniec.endpoint";
		public static final String TIMEOUT = "configuracion.reniec.timeout";
		public static final String USUARIO_RENIEC = "configuracion.reniec.usuario";
		public static final String PASSWORD_RENIEC = "configuracion.reniec.password";
		public static final String CLIENTE_RENIEC = "configuracion.reniec.cliente";
		public static final String ROL_RENIEC = "configuracion.reniec.rol";
		public static final String CODIGO_APLICATIVO_RENIEC = "configuracion.reniec.codigo.aplicativo";
		public static final String MOTIVO_RENIEC = "configuracion.reniec.motivo";
	}
	
	public class PjSeguridad{
		public static final String SECRET_TOKEN="configuracion.pjSeguridad.secretToken";
	}
	
	public class Pide {
		public static final String PARAM_CONFIG_ENDPOINT_PIDE = "configuracion.wspide.endpoint";
		public static final String PARAM_CONFIG_TIMEOUT_PIDE = "configuracion.wspide.timeout";
		public static final String PARAM_CONFIG_USER_PIDE = "configuracion.wspide.user";
		public static final String PARAM_CONFIG_PASS_PIDE = "configuracion.wspide.pass";
		public static final String PARAM_CONFIG_CODIGO_APLICATIVO_PIDE = "configuracion.wspide.appCode";
		public static final String PARAM_CONFIG_CODIGO_ROL_PIDE = "configuracion.wspide.rol";
		public static final String PARAM_CONFIG_CODIGO_CLIENTE_PIDE = "configuracion.wspide.clientCode";
	}

	public class Config{
		public static final String PARAM_CONFIG_MOSTRAR_CONDENA_MAX_DIAS = "configuracion.condenas.mostrar.maxDias";
	}
	
	public class Procedimiento {
		public static final String SP_CONSULTAR_PRISION_PREVENTIVA = "usp_BusquedaPrisionPrev";
		
	}
	
}
