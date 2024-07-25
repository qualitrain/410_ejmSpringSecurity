package mx.com.qtx.ejmSpSec.seguridad.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import mx.com.qtx.ejmSpSec.seguridad.servicios.IGeneradorTokensJWT;
import mx.com.qtx.ejmSpSec.seguridad.servicios.ServicioUserDetailQtx;
import mx.com.qtx.ejmSpSec.seguridad.util.FiltroMonitoreo;
import mx.com.qtx.ejmSpSec.seguridad.util.GeneradorTokensJWT_JJWT0_12_6_SHA512;
import mx.com.qtx.ejmSpSec.seguridad.web.FiltroTokensJwt_SS;
import mx.com.qtx.ejmSpSec.seguridad.web.IExtractorTokenJwtPeticionHttp;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	
	private static Logger bitacora = LoggerFactory.getLogger(ConfiguracionSeguridad.class);
	
	@Bean
	@Order(1)	
	SecurityFilterChain getSecurityFilterChainApiWeb(HttpSecurity http, FiltroTokensJwt_SS filtroJWT,
			            @Qualifier("filtroMonitoreo01") FiltroMonitoreo filtroMonitoreo) 
			            		throws Exception {
		filtroMonitoreo.setIdCadenaFiltrado("CadFiltrado API");
		
		http.securityMatchers(config -> config.requestMatchers("/api/**","/api/autenticacion"))
			.authorizeHttpRequests((authorize) ->  authorize
			     .requestMatchers("/api/autenticacion").permitAll()
			     .requestMatchers("/api/**").hasRole("AGENTE")
			)
			.csrf(config -> config.disable())
			.formLogin(config -> config.disable())
			.logout(config -> config.disable())
		  	.addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class)
		  	.addFilterBefore(filtroMonitoreo, WebAsyncManagerIntegrationFilter.class)
		  	.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	@Order(2)	
	SecurityFilterChain getSecurityFilterChainMvc(HttpSecurity http,
            @Qualifier("filtroMonitoreo02") FiltroMonitoreo filtroMonitoreo) 
		    throws Exception {
		
		RequestMatcher urisXatender = this.buildRequestMatcherTodosExcepto("/api/**", "/api/autenticacion");
		
		filtroMonitoreo.setIdCadenaFiltrado("CadFiltrado MVC");
		http.securityMatcher(urisXatender)
			.authorizeHttpRequests((authorize) ->  authorize
			     .requestMatchers("/css/*","/login").permitAll()
				 .requestMatchers("/info","/vistaInfo.html").permitAll()
			     .requestMatchers("/admin/**").hasRole("ADMIN")
			     .requestMatchers("/logistica/**").hasAnyRole("LOGISTICA","ADMIN")
			     .requestMatchers("/**").authenticated()
			)
			.csrf(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.oauth2Login( cust-> cust.loginPage("/login")
					                 .permitAll())
			.formLogin(config -> config.loginPage("/login")
					                   .permitAll()
					                   .successForwardUrl("/vistaExitoLogin")
					  )
			.logout(config -> config.invalidateHttpSession(true).deleteCookies("JSESSIONID"))
		  	.addFilterBefore(filtroMonitoreo, WebAsyncManagerIntegrationFilter.class)
			.sessionManagement(config -> config.maximumSessions(1));

		return http.build();
	}

	private RequestMatcher buildRequestMatcherTodosExcepto(String urlExcluida1, String urlExcluida2) {
		OrRequestMatcher orRequestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(urlExcluida1), 
				                                                 new AntPathRequestMatcher(urlExcluida2)); 
		RequestMatcher patronUrlsXatender = new NegatedRequestMatcher(orRequestMatcher);
		return patronUrlsXatender;
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
    
//  @Bean
    UserDetailsManager getGestorBdUsuariosEsquemaDefault(@Qualifier("bdSegdDefault") DataSource dataSource) {
    	// Ver (y ejecutar): TestEncriptado.proponerCifradoPasswordsApp();
		UserDetails usuarioAlex = User.withUsername("alex")
				.password("{bcrypt}$2a$10$6vEhoAxBN4IkpcceHslnPOGZw/lXv8piL6hQNaAGUbqWKa.cIIwca")
				.roles("USER","AGENTE","ADMIN")
				.build();
			
			UserDetails usuarioDavid = User.withUsername("david")
					.password("{bcrypt}$2a$10$i0rgAykQH/.dm.ZweInlTOmwbYohydgL3MFVYRUjZKWsuEe7jVIc6")
					.roles("AGENTE")
					.build();
			
			UserDetails usuarioTavo = User.withUsername("tavo")
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
    
//    @Bean
    UserDetailsService getGestorBdUsuariosPersonalizada(@Qualifier("bdSegPersonalizada") DataSource dataSource) {
    	//Se usa una BD Personalizada. Ya debe contener los datos de usuarios y roles
		final String QUERY_DATOS_USUARIO_X_NOMBRE = "SELECT usr_nombre, usr_paswd, usr_habilitado "
				+ "FROM usuario WHERE usr_nombre = ?";

		//Los roles deben estar escritos en los registros de la base de datos con el prefijo "ROLE_"
		//Por ejemplo, ROLE_AGENTE o ROLE_LOGISTICA
		final String QUERY_ROLES_X_USUARIO ="SELECT usr_nombre, aut_nombre "
				+ "FROM usuario, autoridad "
				+ "WHERE usr_nombre = ? "
				+ "AND usr_nombre = aut_nombre_usr";
		
		JdbcDaoImpl gestorBdUsuariosPersonalizada = new JdbcDaoImpl();
		gestorBdUsuariosPersonalizada.setDataSource(dataSource);
		gestorBdUsuariosPersonalizada.setUsersByUsernameQuery(QUERY_DATOS_USUARIO_X_NOMBRE);
		gestorBdUsuariosPersonalizada.setAuthoritiesByUsernameQuery(QUERY_ROLES_X_USUARIO);
		
		return gestorBdUsuariosPersonalizada;
    }

    @Bean
    UserDetailsService getGestorBdUsuariosPersonalizadaQtx() {
    	return new ServicioUserDetailQtx();
    }
    
    @Bean
    PasswordEncoder publicarPasswordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
   
	@Bean
	AuthenticationManager publicarAuthenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		bitacora.trace("publicarAuthenticationManager("
				+ userDetailsService.getClass().getSimpleName() + ", "
				+ passwordEncoder.getClass().getSimpleName()
				+ ")");
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		AuthenticationManager authenticationManager = new ProviderManager(authenticationProvider);
		bitacora.info("AuthenticationManager instanciado y publicado: [" 
		              + authenticationManager.getClass().getSimpleName() + "]");
		return authenticationManager;
	}
	
	@Bean
	IGeneradorTokensJWT publicarGeneradorTokensJWT() {
		bitacora.trace("publicarGeneradorTokensJWT()");
		IGeneradorTokensJWT generadorTokens = new GeneradorTokensJWT_JJWT0_12_6_SHA512();
		bitacora.info("IGeneradorTokensJWT instanciado y publicado: ["
				      + generadorTokens.getClass().getSimpleName() + "]");
		return generadorTokens;
	}
	
	@Bean
	FiltroTokensJwt_SS getFiltroRevisorTokenJWT(IExtractorTokenJwtPeticionHttp extractorTokens, UserDetailsService gestorUsuariosSS) {
		FiltroTokensJwt_SS filtroRevisorTokenJWT = new FiltroTokensJwt_SS(extractorTokens,gestorUsuariosSS);
		return filtroRevisorTokenJWT;
	}

	@Bean("filtroMonitoreo01")
	FiltroMonitoreo getFiltroMonitoreoCad01() {
		FiltroMonitoreo filtroMonitoreo = new FiltroMonitoreo();
		return filtroMonitoreo;
	}
	
	@Bean("filtroMonitoreo02")
	FiltroMonitoreo getFiltroMonitoreoCad02() {
		FiltroMonitoreo filtroMonitoreo = new FiltroMonitoreo();
		return filtroMonitoreo;
	}
}
