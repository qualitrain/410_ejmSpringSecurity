package mx.com.qtx.ejmSpSec.seguridad.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;

import mx.com.qtx.ejmSpSec.seguridad.entidades.Usuario;

//@Service
public class ServicioUserDetailQtx implements UserDetailsService {

	@Autowired
	IGestorDatosSec gestorDatos;
	
	private static Logger bitacora = LoggerFactory.getLogger(ServicioUserDetailQtx.class);
	
	public ServicioUserDetailQtx() {
		super();
		bitacora.info("ServicioUserDetailQtx instanciado");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.gestorDatos.getUsuarioXID(username);
		if(usuario != null)
			return new QtxUserDetails(usuario);
		else
			throw new UsernameNotFoundException("Usuario no encontrado:" + username);
	}

}
