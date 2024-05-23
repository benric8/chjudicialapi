package pe.gob.pj.hjudicial.api.app.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pe.gob.pj.hjudicial.service.SeguridadService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SeguridadService seguridadService;
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	/**
	* Descripción : Permite configurar CORS
	* @return CorsConfigurationSource - entrega informacion de configuracion CORS
	* @exception Captura excepcion generica
	*/
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	* Descripción : Permite configurar validaciones de request HTTP 
	* @return void
	* @exception Captura excepcion generica
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.cors().and().csrf().disable()		
		        .authorizeRequests()
		        .antMatchers("/").permitAll()
		        .antMatchers("/healthcheck","/encriptar","/encryptPastFrass").permitAll()
		        .antMatchers("/css/**").permitAll()
		        .anyRequest().authenticated()
		        .and()
		        .addFilter(new JwtAuthorizationFilter(authenticationManager(), seguridadService))
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), seguridadService))				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	* Descripción : Permite configurar la autenticacion
	* @param AuthenticationManagerBuilder auth - gestor de autenticacion
	* @return void
	* @exception Captura excepcion generica
	*/
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth
    	.userDetailsService(customUserDetailsService)
    	.passwordEncoder(passwordEncoder());
	}

	/**
	* Descripción : Permite configurar proveedor de autenticacion
	* @return DaoAuthenticationProvider - entrega informacion de autenticacion
	* @exception Captura excepcion generica
	*/
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void setSegUsuarioService(SeguridadService seguridadService) {
		this.seguridadService = seguridadService;
	}	
}