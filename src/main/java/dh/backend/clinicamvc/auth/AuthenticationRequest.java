package dh.backend.clinicamvc.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de datos que representa una solicitud de autenticación.
 * Incluye las credenciales básicas necesarias para iniciar sesión: correo electrónico y contraseña.
 *
 * Uso de anotaciones Lombok para evitar la escritura de código estándar.
 */
@Data // Genera automáticamente getters, setters, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase usando el patrón Builder.
@NoArgsConstructor // Genera un constructor sin argumentos necesario para ciertas operaciones de reflexión o serialización.
@AllArgsConstructor // Genera un constructor con un argumento para cada campo de la clase.
public class AuthenticationRequest {

    /** Dirección de correo electrónico del usuario. */
    private String email;

    /** Contraseña del usuario. */
    private String password;
}