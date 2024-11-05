package dh.backend.clinicamvc.auth;


import dh.backend.clinicamvc.configuration.JwtService;
import dh.backend.clinicamvc.entity.Role;
import dh.backend.clinicamvc.entity.User;
import dh.backend.clinicamvc.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor /*Remplaza la anotacion @Autowire siempre y cuando sea final el atributo*/
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {

        /* Utlizacion del metodo Builder Lombock */
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) /*  Hashea el password cifrada */
                .role(Role.USER)
                .build(); /*Termina de construir el objeto */

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()     /* Respondemos el token */
                .jwt(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); /*Programacion funcional Generacion de una excepcion si usuario no existe*/
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()     /* Respondemos el token */
                .jwt(jwtToken)
                .build();

    }
}
