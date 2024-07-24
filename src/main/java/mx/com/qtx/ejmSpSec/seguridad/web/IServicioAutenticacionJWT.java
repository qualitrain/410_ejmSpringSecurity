package mx.com.qtx.ejmSpSec.seguridad.web;

import mx.com.qtx.ejmSpSec.seguridad.entidades.Autenticacion;

public interface IServicioAutenticacionJWT {
	IResultadoOperacion registrarAutenticación(Autenticacion aut);
}
