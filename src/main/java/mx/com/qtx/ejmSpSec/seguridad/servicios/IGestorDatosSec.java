package mx.com.qtx.ejmSpSec.seguridad.servicios;

import mx.com.qtx.ejmSpSec.seguridad.entidades.Usuario;

public interface IGestorDatosSec {
	public Usuario getUsuarioXID(String nombreUsuario);

}
