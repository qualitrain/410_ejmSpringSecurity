package mx.com.qtx.ejmSpSec.seguridad.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class UsuarioGoogle {
	private String email;
	private Collection<? extends GrantedAuthority> autoridades;
	private String fecNac;
	private Map<String, Object> claims;
	private String nombre;
	private String genero;
	private String localidad;
	
	public UsuarioGoogle(DefaultOidcUser oidcUser) {
		this.email = oidcUser.getEmail();
		this.autoridades = oidcUser.getAuthorities();
		this.fecNac= oidcUser.getBirthdate();
		this.claims = oidcUser.getClaims();
		this.nombre = oidcUser.getFullName();
		this.genero = oidcUser.getGender();
		this.localidad = oidcUser.getLocale();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<? extends GrantedAuthority> getAutoridades() {
		return autoridades;
	}

	public void setAutoridades(Collection<? extends GrantedAuthority> autoridades) {
		this.autoridades = autoridades;
	}

	public String getFecNac() {
		return fecNac;
	}

	public void setFecNac(String fecNac) {
		this.fecNac = fecNac;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}

	public void setClaims(Map<String, Object> claims) {
		this.claims = claims;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "UsuarioGoogle ["
				+ "email=" + email 
				+ ", nombre=" + nombre 
				+ ", genero=" + genero 
				+ ", localidad=" + localidad 
				+ ", autoridades=" + autoridades 
				+ ", fecNac=" + fecNac 
				+ ", claims=" + claims 
				+ "]";
	}
}
