package dh.backend.clinicamvc.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de datos que representa una respuesta de autenticación.
 * Contiene el token JWT generado tras un inicio de sesión exitoso.
 *
 * Uso de anotaciones Lombok para minimizar el código boilerplate.
 */
@Data // Genera automáticamente getters, setters, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@NoArgsConstructor // Genera un constructor sin argumentos.
@AllArgsConstructor // Genera un constructor que acepta un argumento para cada campo de la clase.
public class AuthenticationResponse {

    /** Token JWT generado tras una autenticación exitosa. */
    private String jwt;
}