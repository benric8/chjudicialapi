package pe.gob.pj.hjudicial.dao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EdicionSybase {

	ASE("dbo"), ASA("dba");

	private static final Logger LOG = LoggerFactory.getLogger(EdicionSybase.class);

	private String esquema; // Para ASE es dbo, ASA dba

	private EdicionSybase(String esquema) {
		this.esquema = esquema;
	}

	public static EdicionSybase getEdicionPorNombre(String nombre) throws IllegalArgumentException {
		EdicionSybase edicion = null;
		if (nombre == null || nombre.trim().isEmpty()) {
			LOG.error("No se ha especificado la edicion de base de datos de tipo Sybase, por lo tanto no se puede determinar el esquema del Procedimiento Almacenado.");
			throw new IllegalArgumentException("No se ha especificado la edicion de base de datos de tipo Sybase, por lo tanto no se puede determinar el esquema del Procedimiento Almacenado.");
		}
		if (EdicionSybase.ASA.name().equals(nombre)) {
			edicion = EdicionSybase.ASA;
		} else if (EdicionSybase.ASE.name().equals(nombre)) {
			edicion = EdicionSybase.ASE;
		} else {
			LOG.error("Se ha especificado el valor de la edicion de base de datos de tipo Sybase, diferente a ASE y ASA.");
			throw new IllegalArgumentException("Se ha especificado el valor de la edicion de base de datos de tipo Sybase, diferente a ASE y ASA.");
		}
		return edicion;
	}

	public String getEsquema() {
		return esquema;
	}

	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

}