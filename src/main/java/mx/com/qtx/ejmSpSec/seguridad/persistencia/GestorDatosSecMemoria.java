package mx.com.qtx.ejmSpSec.seguridad.persistencia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import mx.com.qtx.ejmSpSec.seguridad.entidades.Autoridad;
import mx.com.qtx.ejmSpSec.seguridad.entidades.Usuario;
import mx.com.qtx.ejmSpSec.seguridad.servicios.IGestorDatosSec;

//@Primary
@Repository
public class GestorDatosSecMemoria implements IGestorDatosSec {
	private Map<String,Usuario> usuarios;

	public GestorDatosSecMemoria() {
		this.usuarios = new HashMap<>();
		this.cargarUsuarios();
	}

	private void cargarUsuarios() {
		Usuario usuario = new Usuario("alex","{bcrypt}$2a$10$6vEhoAxBN4IkpcceHslnPOGZw/lXv8piL6hQNaAGUbqWKa.cIIwca",true);
		Set<Autoridad> autoridades = new HashSet<>();
		autoridades.add(new Autoridad(1,"ROLE_ADMIN",usuario));
		autoridades.add(new Autoridad(2,"ROLE_LOGISTICA",usuario));
		usuario.setAutoridades(autoridades);
		
		this.usuarios.put(usuario.getNombre(), usuario);
		
		Usuario usuario2 = new Usuario("david","{bcrypt}$2a$10$i0rgAykQH/.dm.ZweInlTOmwbYohydgL3MFVYRUjZKWsuEe7jVIc6",true);
		Set<Autoridad> autoridades2 = new HashSet<>();
		autoridades2.add(new Autoridad(3,"ROLE_AGENTE",usuario2));
		usuario2.setAutoridades(autoridades2);
		
		this.usuarios.put(usuario2.getNombre(), usuario2);
		
		Usuario usuario3 = new Usuario("tavo","{bcrypt}$2a$10$H1EqBwIs5ZLnnePgZODr5uQpCxgw0wtpkTJhEEzuaSUzRQsFmFqhy",true);
		Set<Autoridad> autoridades3 = new HashSet<>();
		autoridades3.add(new Autoridad(4,"ROLE_LOGISTICA",usuario3));
		usuario3.setAutoridades(autoridades3);
		
		this.usuarios.put(usuario3.getNombre(), usuario3);
		
	}

	@Override
	public Usuario getUsuarioXID(String nombreUsuario) {
		return this.usuarios.get(nombreUsuario);
	}

}
