package dh.backend.clinicamvc.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que asegura que cada solicitud HTTP contiene un token JWT válido.
 * Si el token es válido, se configura el contexto de seguridad para el usuario autenticado.
 */
@Configuration
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Servicio para manejar y validar operaciones de JWT. */
    private final JwtService jwtService;

    /** Servicio para cargar detalles de usuario a partir del almacén de usuarios. */
    private final UserDetailsService userDetailsService;

    /**
     * Método que se ejecuta por cada solicitud HTTP.
     *
     * @param request La solicitud HTTP actual.
     * @param response La respuesta HTTP actual.
     * @param filterChain La cadena de filtros de seguridad aplicada a la solicitud.
     * @throws ServletException si ocurre un error en el procesamiento del filtro.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtiene la cabecera de autorización de la solicitud
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Si no hay token o no empieza con "Bearer ", se continúa sin validación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el token JWT desde la cabecera
        jwt = authHeader.substring(7);
        // Extrae el email de usuario desde el token JWT
        userEmail = jwtService.extractUsername(jwt);

        // Chequea si el usuario es válido y no hay otra autenticación activa
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Valida el token JWT con el UserDetails
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Extrae roles y construye el token de autenticación
                var roles = jwtService.extractAuthorities(jwt);

                // Imprimir cada rol
                roles.forEach(role -> System.out.println("Role in JWT: " + role.getAuthority()));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, roles);

                // Configura los detalles de autenticación
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto actual
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}