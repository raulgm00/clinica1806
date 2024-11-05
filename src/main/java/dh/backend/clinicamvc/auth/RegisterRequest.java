package dh.backend.clinicamvc.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de datos que representa una solicitud de registro de usuario.
 * Contiene la información básica requerida para registrar un nuevo usuario en el sistema.
 *
 * Utiliza anotaciones de Lombok para generar automáticamente métodos estándar y constructores.
 */
@Data // Genera automáticamente getters, setters, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@NoArgsConstructor // Genera un constructor sin argumentos.
@AllArgsConstructor // Genera un constructor que acepta un argumento para cada campo de la clase.
public class RegisterRequest {

    /** Nombre del usuario que se desea registrar. */
    private String firstname;

    /** Apellido del usuario que se desea registrar. */
    private String lastname;

    /** Dirección de correo electrónico única y válida del usuario. */
    private String email;

    /** Contraseña del usuario, que debería ser cifrada antes de almacenarse. */
    private String password;
}