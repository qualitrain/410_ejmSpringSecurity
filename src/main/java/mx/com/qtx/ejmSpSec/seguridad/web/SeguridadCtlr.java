package mx.com.qtx.ejmSpSec.seguridad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeguridadCtlr {
	
	private static Logger bitacora =  LoggerFactory.getLogger(SeguridadCtlr.class);

	  
	public SeguridadCtlr() {
		bitacora.info("Controlador instanciado:LoginCtlr()");
	}

	@GetMapping("/login")
	String login() {
		bitacora.info("login()");
		return "seguridad/loginConOAuth2";
	}
	
	@GetMapping("/logout")
	String logout() {
		bitacora.info("logout()");
		return "seguridad/logout";
	}
	
	// Para soportar el cambio en la URL de éxito en la autenticación
	@PostMapping("/vistaExitoLogin")
	public String getVistaInicial(Model modelo) {
		return "seguridad/vistaUsuariosAutenticados";
	}
	
	@GetMapping("/usuario_comprometido")
	public String getAvisoProblemaCuenta() {
		return "seguridad/vistaAvisoProblemaCuenta";
	}
}
