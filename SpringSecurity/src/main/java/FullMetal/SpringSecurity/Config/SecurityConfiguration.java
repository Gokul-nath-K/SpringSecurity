package FullMetal.SpringSecurity.Config;

import FullMetal.SpringSecurity.Constants.Api;
import static FullMetal.SpringSecurity.Models.Enumerate.Role.*;
import static FullMetal.SpringSecurity.Models.Enumerate.Permission.*;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import FullMetal.SpringSecurity.Utils.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAthenticationFilter jwtAthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    private static final String[] PublicEndPoints = {
            "/api/auth/**",
            "/api/web/sites",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/v3/api-docs/**"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfirguarationSource -> corsConfirguarationSource.configurationSource(
                        corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PublicEndPoints)
                        .permitAll()
                        .requestMatchers(Api.USER+ "/**")
                        .hasRole(ADMIN.name())
                        .requestMatchers(GET, Api.USER + "/**")
                        .hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, Api.USER + "/**")
                        .hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, Api.USER + "/**")
                        .hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, Api.USER + "/**")
                        .hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers(Api.USER + "/**")
                        .hasAnyRole(ADMIN.name(), USER.name())
                        .requestMatchers(GET, Api.USER + "/**")
                        .hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                        .requestMatchers(POST, Api.USER + "/**")
                        .hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                        .requestMatchers(PUT, Api.USER + "/**")
                        .hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                        .requestMatchers(DELETE, Api.USER + "/**")
                        .hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())

                        .anyRequest()
                        .authenticated())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
