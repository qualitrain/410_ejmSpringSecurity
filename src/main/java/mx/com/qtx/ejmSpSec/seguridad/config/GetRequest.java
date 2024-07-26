package mx.com.qtx.ejmSpSec.seguridad.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class GetRequest extends HttpServletRequestWrapper {

	public GetRequest(HttpServletRequest req) {
		super(req);
	}
	@Override
	public String getMethod() {
		return "GET";
	}
}
