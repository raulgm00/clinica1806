package dh.backend.clinicamvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data                   /* Lombock Es la suma de Getter, Setter, Hascode, ToString */
@Builder                /*Lombock  Utiliza el patron Builder que nos ayuda a generar , sin hacer un new detallado*/
@AllArgsConstructor     /*Lombock: genera un constructor que toma un argumento para cada campo en la clase. Esto es útil cuando deseas crear instancias de la clase proporcionando valores iniciales para todos sus campos*/
@NoArgsConstructor      /*Lombock: genera un constructor sin argumentos. Esto es útil cuando necesitas una instancia de la clase sin inicializar sus campos o cuando la clase se utiliza en frameworks que requieren un constructor sin argumentos, como JPA */
@Entity                 /*Indica una entidad tabla */
@Table(name = "users")  /*Indica una tabla en JPA*/
public class User implements UserDetails { /*Implementa una interface que se llama UserDetails de Spring Security: Metodos de autenticacion */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING) /*Salvedad para definir el dato exacto dentro de la eneumeracion, por nombre = string */
    private Role role;

    /*Interface que accede a la Lista de permisos ROLES */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); /*Impl d ela interfaz con respecto a todos los roles de la clase ROLE */
    }

    /* Agregaremos el atributo por el cual identificaremos al usuario logeado USERNAME = [MAIL]*/
    @Override
    public String getUsername() {
        return email;
    }

    /* Metodo para validar si la cuenta no esta expirada, colocarlo en TRUE */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* Metodo para validar si la cuenta no esta bloqueada, colocarlo en TRUE */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* Metodo para validar si el password no esta expirado, colocarlo en TRUE */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* Metodo para validar si la cuenta no esta deshabilitada, colocarlo en TRUE */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
