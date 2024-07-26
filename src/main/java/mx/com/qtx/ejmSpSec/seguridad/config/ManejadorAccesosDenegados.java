package mx.com.qtx.ejmSpSec.seguridad.config;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ManejadorAccesosDenegados implements AccessDeniedHandler{
	private static Logger bitacora = LoggerFactory.getLogger(ManejadorAccesosDenegados.class);
	private AccessDeniedHandlerImpl accessDeniedHandlerUsual;
	private List<String> usuariosComprometidos;

	public ManejadorAccesosDenegados() {
		bitacora.info("AccessDeniedHandlerMonitoreo instanciado");
		this.accessDeniedHandlerUsual = new AccessDeniedHandlerImpl();
		this.usuariosComprometidos = this.cargarListaUsuariosComprometidos();
	}
	private List<String> cargarListaUsuariosComprometidos() {
		return List.of("tavo","lucrecia","betote");
	}
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		String causaOrigen = accessDeniedException.getCause() == null ? "----" : accessDeniedException.getCause().getMessage();
		String nombreUsuario = request.getUserPrincipal().getName();
		String descAcceso = request.getMethod() + " " + request.getServletPath();
		
		bitacora.warn("handle( " + descAcceso + ", "
				       + response.getStatus() + ", " + accessDeniedException.getMessage() 
				       + ":" + accessDeniedException.getClass().getName() + ", causada por "
				       		+ causaOrigen + " )");
		
		bitacora.warn(nombreUsuario + " intentó un acceso NO AUTORIZADO (" 
				       + descAcceso +  "), " + (new Date()));
		
		if(this.usuariosComprometidos.contains(nombreUsuario)) {			
			RequestDispatcher rd = request.getRequestDispatcher("/usuario_comprometido");
			GetRequest reqWrap = new GetRequest(request);
			rd.forward(reqWrap, response);
		}
		else
			this.accessDeniedHandlerUsual.handle(request, response, accessDeniedException);
	}
}
