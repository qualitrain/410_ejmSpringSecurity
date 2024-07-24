package mx.com.qtx.ejmSpSec;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import mx.com.qtx.ejmSpSec.seguridad.web.jaxrs.AutenticacionRest;
import mx.com.qtx.ejmSpSec.web.api.jaxrs.CatalogosRest;
import mx.com.qtx.ejmSpSec.web.api.jaxrs.CorsFilter;
import mx.com.qtx.ejmSpSec.web.api.jaxrs.PersonasRest;

@Configuration
public class ConfiguracionJersey extends ResourceConfig {
	
	private static Logger bitacora = LoggerFactory.getLogger(ConfiguracionJersey.class);
	 
	public ConfiguracionJersey() {
		
		bitacora.trace("ConfiguracionJersey()");
		
		this.register(CatalogosRest.class);
		this.register(PersonasRest.class);
		this.register(AutenticacionRest.class);
		this.register(CorsFilter.class);
	}
}
