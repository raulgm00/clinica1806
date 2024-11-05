package dh.backend.clinicamvc.configuration;

import dh.backend.clinicamvc.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servicio que proporciona funcionalidad para generar y validar tokens JWT.
 * Utilizado para autenticar usuarios y gestionar su rol/autoridad dentro de la aplicación.
 */
@Service
public class JwtService {

    /** Clave secreta utilizada para la firma de tokens JWT. */
    private static final String SECRET_KEY = "c43b340820d99f52de5e8162c4b43d507d49bd39ba30085d5885d7bc1f6cda27";

    /**
     * Genera un token JWT para el usuario dado.
     * Incluye roles de usuario como parte de los claims.
     *
     * @param userDetails Detalles del usuario para los cuales se debe generar el token.
     * @return El token JWT generado.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("role", userDetails.getAuthorities().stream()
                .map(grantedAuthority -> "ROLE_" + grantedAuthority.getAuthority())
                .collect(Collectors.toList()));
        return generateToken(claims, userDetails);
    }

    /**
     * Genera un token JWT con los claims y detalles de usuario proporcionados.
     *
     * @param extractClaims Claims a incluir en el token.
     * @param userDetails Detalles del usuario.
     * @return El token JWT generado.
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira en 24 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Decodifica la clave secreta para firmar el token.
     *
     * @return La clave decodificada.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token Token JWT del cual extraer el nombre de usuario.
     * @return El nombre de usuario extraído.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim específico del token JWT.
     *
     * @param token Token JWT del cual extraer los claims.
     * @param claimsTFunction Función para aplicar a los claims.
     * @param <T> Tipo del claim extraído.
     * @return El claim extraído.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Extrae todos los claims del token JWT.
     *
     * @param token Token JWT del cual extraer los claims.
     * @return Los claims extraídos.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Valida si el token JWT es válido y coincide con el usuario proporcionado.
     *
     * @param token Token JWT a validar.
     * @param userDetails Detalles del usuario para validar.
     * @return `true` si el token es válido; `false` de lo contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Comprueba si el token JWT ha expirado.
     *
     * @param token Token JWT a verificar.
     * @return `true` si el token ha expirado, `false` de lo contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token Token JWT del cual extraer la fecha de expiración.
     * @return La fecha de expiración del token.
     */
    private Date extractExpiration(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        System.out.println("Fecha de expiración del token: " + expirationDate);
        return expirationDate;
    }

    /**
     * Extrae las autoridades (roles) del token JWT.
     *
     * @param token Token JWT del cual extraer las autoridades.
     * @return Una colección de autoridades extraídas del token.
     */
    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("role", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
