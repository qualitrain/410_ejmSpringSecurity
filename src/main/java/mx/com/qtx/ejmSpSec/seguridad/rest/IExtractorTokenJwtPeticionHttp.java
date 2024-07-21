package mx.com.qtx.ejmSpSec.seguridad.rest;

import jakarta.servlet.http.HttpServletRequest;

public interface IExtractorTokenJwtPeticionHttp {
	boolean peticionTieneTokenValido(HttpServletRequest request);
	String getNombreUsuario(HttpServletRequest request);
}
