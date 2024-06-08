package viet.spring.SonicServer.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.transaction.Transactional;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.jwt.JwtAuthenticationFilter;
import viet.spring.SonicServer.service.UserService;
import org.springframework.security.config.annotation.web.configuration.*;

/**
 * SecurityConfig
 * 
 * isAuthenticated(): Kiểm tra xem người dùng đã được xác thực hay chưa. Ví dụ:
 * isAuthenticated() sẽ kiểm tra xem người dùng đã được xác thực hay chưa .
 */
@Configuration
@EnableWebSecurity
//@AllArgsConstructor
public class SecurityConfig {
	@Autowired
	private UserService userS;
//	@Autowired
//	private DataSource dataSource;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			@Transactional
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Optional<User> userO = userS.findByUsername(username);

				if (userO.isEmpty()) {
					System.out.println("Không tìm thấy người dùng! " + username);
					throw new UsernameNotFoundException(
							"Người dùng " + username + " không được tìm thấy trong cơ sở dữ liệu");
				}

				User user = userO.get();
				List<Role> roles = user.getRoles();
				List<String> names = roles.stream().map(t -> t.getName()).toList();

				List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
				if (names != null) {
					for (String name : names) {
						GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + name);
						grantList.add(authority);
					}
				}

				UserDetails userDetails = new UserImplUserDetails(username, user.getPassword(), grantList);

				return userDetails;
			}
		};
	}
	

	@Bean
	public BCryptPasswordEncoder encoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
			throws Exception {

		http
//		.csrf(c -> c
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			)
//				.cors(Customizer.withDefaults())
				.cors(cors -> cors.disable())
				.csrf(t -> t.disable())
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/sonic/lib/**", "/security/getUser", "/sonic/user/**")
								.hasAnyRole("USER", "ADMIN")// có một trong số các quyền
								.requestMatchers("/sonic/amdin/**").hasRole("ADMIN")// chỉ có quyền admin mơi được thực
																					// hiện
								.anyRequest().permitAll())
//				.oauth2Login(Customizer.withDefaults()).formLogin(Customizer.withDefaults())
				.logout(l -> l.disable())
				.httpBasic(Customizer.withDefaults())
				// Khi người dùng đã login, với vai trò XX.
				// Nhưng truy cập vào trang yêu cầu vai trò YY,
				// Ngoại lệ AccessDeniedException sẽ ném ra.
				// .exceptionHandling(t -> t.accessDeniedPage("/403"))

				// Cấu hình cho Login Form.
//			.formLogin(t -> t.loginProcessingUrl("/j_spring_security_check") // Submit URL
//					.loginPage("/DangNhap")//
//					.defaultSuccessUrl("/")//
//					.failureUrl("/DangNhap?LoiDangNhap=true")//
//					.usernameParameter("tenDangNhap")//
//					.passwordParameter("matKhau"))

				// Cấu hình cho Logout Page.
//			.logout(t -> t.logoutUrl("/api/"))

				// Cấu hình Remember Me.

//			.rememberMe(t -> t.tokenRepository(this.persistentTokenRepository())
//				.tokenValiditySeconds(1*24*60*60))//24h
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		

		return http.build();
	}

//	@Bean
//	public PersistentTokenRepository persistentTokenRepository() {
//		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//		db.setDataSource(dataSource);
//		return db;
//	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
//		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("Authorization"));
//		configuration    .setAllowedHeaders(Arrays.asList("*"));
//		configuration.setExposedHeaders(Arrays.asList("Authorization Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}

}
