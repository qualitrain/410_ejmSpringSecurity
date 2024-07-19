package mx.com.qtx.ejmSpSec.seguridad.rest.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.qtx.ejmSpSec.seguridad.entidades.Autenticacion;
import mx.com.qtx.ejmSpSec.seguridad.entidades.TokenJWT;
import mx.com.qtx.ejmSpSec.seguridad.rest.IResultadoOperacion;
import mx.com.qtx.ejmSpSec.seguridad.rest.IServicioAutenticacionJWT;

@Path("autenticacion")
public class AutenticacionRest {
	
	@Autowired
	private IServicioAutenticacionJWT servAutenticacion;
	
	private static Logger bitacora = LoggerFactory.getLogger(AutenticacionRest.class);
	 
	public AutenticacionRest() {
		bitacora.info("***** Se ha instanciado el EndPoint de Autenticación");
	}
	
	public IServicioAutenticacionJWT getServAutenticacion() {
		return servAutenticacion;
	}
	
	public void setServAutenticacion(IServicioAutenticacionJWT servAutenticacion) {
		this.servAutenticacion = servAutenticacion;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TokenJWT registrarAutenticacion(Autenticacion aut) {
		bitacora.info("registrarAutenticacion(" + aut + ")");
		IResultadoOperacion resultado = this.servAutenticacion.registrarAutenticación(aut);
		if(resultado.todoOk())
			return (TokenJWT) resultado.getObjResultadoOk();
		
		throw ErrorRest.getError(resultado.getResumenErrores(), 
				                 ErrorRest.ERR_AUTENTICACION_FALLIDA, 
				                 Response.Status.UNAUTHORIZED);

	}
	
}
