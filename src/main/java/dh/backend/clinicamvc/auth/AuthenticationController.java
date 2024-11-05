package dh.backend.clinicamvc.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador REST para gestionar las funcionalidades de autenticación.
 * Proporciona endpoints para el registro y el inicio de sesión de usuarios.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    /** Servicio de autenticación que gestiona la lógica de negocio para registros y logins. */
    private final AuthenticationService authenticationService;



    /**
     * Endpoint para el registro de nuevos usuarios.
     *
     * @param request Objeto {@link RegisterRequest} que contiene la información del usuario a registrar.
     * @return {@link ResponseEntity} con un objeto {@link AuthenticationResponse} que contiene el JWT generado.
     */
    @PostMapping("/registro")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Endpoint para el inicio de sesión de usuarios.
     * Valida las credenciales del usuario y genera un token JWT si la autenticación es exitosa.
     *
     * @param request Objeto {@link AuthenticationRequest} que contiene las credenciales del usuario.
     * @return {@link ResponseEntity} con un objeto {@link AuthenticationResponse} que contiene el JWT generado.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        System.out.println("Ingreso al método de login");
        return ResponseEntity.ok(authenticationService.login(request));
    }
}