package mx.com.qtx.ejmSpSec.seguridad.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FiltroMonitoreo extends OncePerRequestFilter {
	private String idCAdenaFiltrado;
	
	private static Logger bitacora = LoggerFactory.getLogger(FiltroMonitoreo.class);

	public FiltroMonitoreo() {
		super();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		bitacora.info("*************** " + this.idCAdenaFiltrado + " ***************");
		bitacora.info("*** doFilterInternal ANTES DE CAD FILTRADO (servletPath:" + request.getServletPath() 
		 			+ ", URI:" + request.getRequestURI()
		 		    + ", PathInfo:" + request.getPathInfo()
				    + ")");
		
		 SecurityContext ctxSeguridad = SecurityContextHolder.getContext();
		 Authentication principal = ctxSeguridad.getAuthentication();
		 if(principal != null) {
			 bitacora.info("Ya hay un Principal registrado: " + principal.getName() 
			             + ", Autoridades:" + principal.getAuthorities());
		 }
		 else
			 bitacora.info("No hay un Principal registrado");
		
		
		filterChain.doFilter(request, response);
		
		bitacora.info("=== doFilterInternal DESPUES DE CAD FILTROS (servletPath:" + request.getServletPath() 
			+ ", URI:" + request.getRequestURI()
			+ ", PathInfo:" + request.getPathInfo()
			+ ")");
		 ctxSeguridad = SecurityContextHolder.getContext();
		 principal = ctxSeguridad.getAuthentication();
		 if(principal != null) {
			 bitacora.info("Ya hay un Principal registrado: " + principal.getName() 
			             + ", Autoridades:" + principal.getAuthorities());
		 }
		 else
			 bitacora.info("No hay un Principal registrado");
		bitacora.info("=============== " + this.idCAdenaFiltrado + " ===============");
	}

	public String getIdCAdenaFiltrado() {
		return idCAdenaFiltrado;
	}

	public void setIdCadenaFiltrado(String idCAdenaFiltrado) {
		this.idCAdenaFiltrado = idCAdenaFiltrado;
	}


}
