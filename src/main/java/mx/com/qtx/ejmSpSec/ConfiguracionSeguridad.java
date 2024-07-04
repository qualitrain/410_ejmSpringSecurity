package mx.com.qtx.ejmSpSec;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import mx.com.qtx.ejmSpSec.seguridad.util.TestEncriptado;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) ->  authorize
			     .requestMatchers("/css/*").permitAll()
				 .requestMatchers("/info","/vistaInfo.html").permitAll()
			     .requestMatchers("/api/**").hasRole("AGENTE")
			     .requestMatchers("/admin/**").hasRole("ADMIN")
			     .requestMatchers("/logistica/**").hasAnyRole("LOGISTICA","ADMIN")
			     .requestMatchers("/**").authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

 // @Bean
    UserDetailsService userDetailsService() {
		UserDetails userDetailsAlex = User.withDefaultPasswordEncoder()
			.username("alex")
			.password("tekamachalko")
			.roles("USER","AGENTE","ADMIN")
			.build();
		
		UserDetails userDetailsDavid = User.withDefaultPasswordEncoder()
				.username("david")
				.password("tekolutla")
				.roles("AGENTE")
				.build();
		
		UserDetails userDetailsTavo = User.withDefaultPasswordEncoder()
				.username("tavo")
				.password("tlatelolko")
				.roles("LOGISTICA")
				.build();		

		return new InMemoryUserDetailsManager(userDetailsAlex, userDetailsDavid, userDetailsTavo);
	}
    
    @Bean
    UserDetailsManager getGestorBdUsuarios(DataSource dataSource) {
    	// Ver (y ejecutar): TestEncriptado.proponerCifradoPasswordsApp();
		UserDetails usuarioAlex = User.withUsername("alex")
				.password("{bcrypt}$2a$10$6vEhoAxBN4IkpcceHslnPOGZw/lXv8piL6hQNaAGUbqWKa.cIIwca")
				.roles("USER","AGENTE","ADMIN")
				.build();
			
			UserDetails usuarioDavid = User.withUsername("david")
					.password("{bcrypt}$2a$10$i0rgAykQH/.dm.ZweInlTOmwbYohydgL3MFVYRUjZKWsuEe7jVIc6")
					.roles("AGENTE")
					.build();
			
			UserDetails usuarioTavo = User.withUsername("alex")
					.password("{bcrypt}$2a$10$H1EqBwIs5ZLnnePgZODr5uQpCxgw0wtpkTJhEEzuaSUzRQsFmFqhy")
					.roles("LOGISTICA")
					.build();		

    	JdbcUserDetailsManager gestorUsuariosBD = new JdbcUserDetailsManager(dataSource);
    	
    	if(gestorUsuariosBD.userExists(usuarioAlex.getUsername()) == false)
    	   gestorUsuariosBD.createUser(usuarioAlex);
       	if(gestorUsuariosBD.userExists(usuarioDavid.getUsername()) == false)
       	   gestorUsuariosBD.createUser(usuarioDavid);
       	if(gestorUsuariosBD.userExists(usuarioTavo.getUsername()) == false)
       	   gestorUsuariosBD.createUser(usuarioTavo);
    	
    	return gestorUsuariosBD;
    }    

}
