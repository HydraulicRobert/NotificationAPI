package com.proxy.notifications.configuration;


import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import com.proxy.notifications.filter.JwtAuthenticationFilter;
import com.proxy.notifications.filter.PostFilterLogFilter;
import com.proxy.notifications.jwt.JwtUtils;
@Configuration
@EnableWebSecurity
public class WebsecurityConfig {
	private final JwtUtils jwtUtils;
	private JwtAuthenticationFilter jwtAuthFilter;
	private PostFilterLogFilter postFilterLogFilter;
	private final UserDetailsService userDetailsService;
	/*private final RsaKeyProperties rsaKeyProperties;
	@Value("${spring.security.oauth2.client.registration.github.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.client.registration.github.client-secret}")
	private String clientSecret;*/
	public WebsecurityConfig(
			//RsaKeyProperties rsaKeyProperties, 
			JwtUtils jwtUtils, 
			UserDetailsService userDetailsService,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		//this.rsaKeyProperties = rsaKeyProperties;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
		this.jwtAuthFilter = new JwtAuthenticationFilter(jwtUtils, userDetailsService);
		this.postFilterLogFilter = new PostFilterLogFilter();
	}
	
	@Bean
	@Order(1)
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http
			//,OAuth2AuthorizedClientService clientService
			) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
	   		.authorizeHttpRequests(auth -> auth
	   				.requestMatchers("/","/login","/default-ui.css","/favicon.ico","/error").permitAll()
	   				.anyRequest().authenticated()
	   				)
	   		.sessionManagement(session -> session
	   				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	   				)
	   		.authenticationProvider(
	   				authenticationProvider(
	   						userDetailsService, 
	   						passwordEncoder()
	   				)
	   		)
	   		.addFilterBefore(jwtAuthFilter, AbstractPreAuthenticatedProcessingFilter.class)
	   		.addFilterAfter(postFilterLogFilter, AbstractPreAuthenticatedProcessingFilter.class)
	   		;

		return http.build();

	}

	@Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration
			) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider (
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder
	) {
		DaoAuthenticationProvider daoAuthenticationProvider =
		new DaoAuthenticationProvider(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	/*@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder
				.withPublicKey(rsaKeyProperties.publicKey())
				.build();
	}
	
	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}*/
	
	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
		manager.setAllowNullValues(false);
		manager.setCacheNames(Arrays.asList("sqlChkNotAllTop",
											"sqlChkNotAllBottom",
											"sqlChkNotTop1",
											"sqlChkSet",
											"sqlChk6Sec",
											"notificationList",
											"sixSecondTimestamp",
											"settingsTimestamp",
											"mostCurrentNotificationTimestamp",
											"mostCurrentNotificationTimestampAll",
											"userList",
											"userTokenCache"));
		return manager;
	}
}
