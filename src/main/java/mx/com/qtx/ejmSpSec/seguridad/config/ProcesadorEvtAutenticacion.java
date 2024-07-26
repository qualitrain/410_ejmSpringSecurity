package mx.com.qtx.ejmSpSec.seguridad.config;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class ProcesadorEvtAutenticacion {
	private static Logger bitacora =  LoggerFactory.getLogger(ProcesadorEvtAutenticacion.class);
	
	@EventListener
	public void alAutenticarExitosamente(AuthenticationSuccessEvent autOkEvt) {
		Authentication autenticacion = autOkEvt.getAuthentication();
		
		if(this.esUsuarioOpenIDConnect(autenticacion)) {
			DefaultOidcUser principal = (DefaultOidcUser) autenticacion.getPrincipal();
			UsuarioGoogle usuarioGoogle = new UsuarioGoogle(principal);
			bitacora.info(usuarioGoogle.toString());
		}
		
		this.mostrarAutenticacion(autenticacion);
	}
	@EventListener
	public void alFallarAutenticacion(AbstractAuthenticationFailureEvent autFallaEvt) {
		String nomUsuario="[no especificado]";
		if(autFallaEvt.getAuthentication() != null)
			nomUsuario = autFallaEvt.getAuthentication().getName();
		bitacora.info("Autenticación fallida de usuario [" + nomUsuario
				+ "], Tipo Falla: " + autFallaEvt.getException().getClass().getSimpleName());	
	}
	
	private boolean esUsuarioOpenIDConnect(Authentication aut) {
		Object principal = aut.getPrincipal();
		if(principal instanceof DefaultOidcUser)
			return true;
		return false;
	}
	
	private void mostrarAutenticacion(Authentication autenticacion) {
		String nombreUsuario = autenticacion.getName();
		ArrayList<String> listAutoridades = new ArrayList<String>();
		autenticacion.getAuthorities().stream()
		                              .map(autI -> autI.toString())
		                              .forEach( cadI -> listAutoridades.add(cadI));
		WebAuthenticationDetails detalles = (WebAuthenticationDetails) autenticacion.getDetails();

		bitacora.info("Autenticación exitosa:");
		bitacora.info("usuario:" + nombreUsuario);
		bitacora.info("autoridades:" + listAutoridades.toString());
		bitacora.info("clase Details:" + autenticacion.getDetails().getClass().getName());
		bitacora.info("clase Authentication:" + autenticacion.getClass().getName());
		bitacora.info("clase Principal:" + autenticacion.getPrincipal().getClass().getName());
		bitacora.info("sesión:" + detalles.getSessionId());
		
	}
}
