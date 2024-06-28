package mx.com.qtx.ejmSpSec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize.anyRequest()
					                                       .authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
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

}
