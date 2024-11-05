package dh.backend.clinicamvc.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación usando Spring Security.
 * Define políticas de autorización para los endpoints y configura la gestión de sesiones.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /** Proveedor de autenticación para la gestión de credenciales. */
    private final AuthenticationProvider authenticationProvider;

    /** Filtro de autenticación JWT para validar tokens en cada solicitud. */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura la cadena de filtros de seguridad de Spring Security.
     *
     * @param http Estructura que permite configurar la seguridad basada en HTTP.
     * @return {@link SecurityFilterChain} configurada para la aplicación.
     * @throws Exception si ocurre algún error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth -> {
                    // ************ Endpoints que no requieren autenticación ************
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/odontologos/**").permitAll();

                    // Acceso a la consola de H2 sin autenticación
                    auth.requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/**").permitAll();

                    // Acceso a Swagger UI sin autenticación
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll();

                    // ************ Endpoints con roles específicos ************
                    // Unificación de roles para operaciones en odontólogos
                    auth.requestMatchers(HttpMethod.PUT, "/odontologos/**").hasAnyRole("USER", "ADMIN");

                    // Maneja otras operaciones similares para otros endpoints
                    auth.requestMatchers(HttpMethod.POST, "/odontologos/**").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/odontologos/**").hasAnyRole("USER", "ADMIN");

                    auth.requestMatchers(HttpMethod.POST, "/pacientes/**").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/pacientes/**").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/pacientes/**").hasAnyRole("USER", "ADMIN");

                    // ************ Endpoints que requieren autenticación ************
                    auth.requestMatchers("/turnos/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/pacientes/**").authenticated();

                    // Todas las demás solicitudes requieren autenticación
                    auth.anyRequest().authenticated();
                })
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }
}