package com.project.allocation.security;

import com.project.allocation.repository.UserRepository;
import com.project.allocation.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

/**
 * Security configuration class for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    private final JwtFilter jwtFilter;

    public SecurityConfig(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtFilter = new JwtFilter(jwtUtil, userRepository);
    }

    /**
     * Configures user details service for authentication.
     *
     * @param dataSource the data source.
     * @return the user details service.
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to configure.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/login").permitAll() // login endpoint
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // register endpoint
                                .requestMatchers("/error").permitAll() // allow access to /error for debugging
                                .requestMatchers("/api/auth/hello_staff").hasRole("STAFF")
                                .requestMatchers("/api/auth/hello_student").hasRole("STUDENT")
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a password encoder bean.
     *
     * @return the password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures granted authority defaults to remove the "ROLE_" prefix.
     *
     * @return the granted authority defaults.
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    /**
     * Configures CORS settings for the application.
     *
     * @return the WebMvcConfigurer with CORS configurations.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}