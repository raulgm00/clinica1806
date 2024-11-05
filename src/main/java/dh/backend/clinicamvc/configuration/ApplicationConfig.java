package dh.backend.clinicamvc.configuration;

import dh.backend.clinicamvc.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para establecer los componentes de seguridad de la aplicación.
 * Configura servicios fundamentales como `UserDetailsService`, `AuthenticationProvider`, `PasswordEncoder`, y `AuthenticationManager`.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /** Repositorio de usuarios para acceder a datos de usuario en la base de datos. */
    private final IUserRepository userRepository;

    /**
     * Configura el servicio de detalles de usuario.
     * Busca usuarios por correo electrónico y lanza una excepción si el usuario no se encuentra.
     *
     * @return una instancia de {@link UserDetailsService}.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    /**
     * Configura el proveedor de autenticación utilizando un `DaoAuthenticationProvider`.
     * Utiliza el `UserDetailsService` y el `PasswordEncoder` configurados.
     *
     * @return una instancia de {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura el codificador de contraseñas utilizando `BCrypt`.
     * Proporciona cifrado seguro para almacenar contraseñas.
     *
     * @return una instancia de {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el manejador de autenticación principal de la aplicación.
     *
     * @param config configuración de autenticación de Spring.
     * @return una instancia de {@link AuthenticationManager}.
     * @throws Exception si falla la configuración del manejador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}