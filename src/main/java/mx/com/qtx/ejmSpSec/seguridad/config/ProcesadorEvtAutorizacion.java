package mx.com.qtx.ejmSpSec.seguridad.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProcesadorEvtAutorizacion {
	private static Logger bitacora =  LoggerFactory.getLogger(ProcesadorEvtAutorizacion.class);
	
	@EventListener
	public void alDenegarAutorizacio(AuthorizationDeniedEvent<Object> autRechazadaEvt) {
		String principal = autRechazadaEvt.getAuthentication().get().getName();
		String decision = autRechazadaEvt.getAuthorizationDecision().toString();
		String recurso = autRechazadaEvt.getObject().toString();
		bitacora.info("Autorizacion Denegada. " + "usuario " + principal 
				+ " recurso:" + recurso + ", decision:" + decision);
		
	}

}
