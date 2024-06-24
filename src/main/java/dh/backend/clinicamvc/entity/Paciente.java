package dh.backend.clinicamvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name ="pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;


    /**
     * Relacion Unidireccional Paciente tine un Domicilio
     * El Domicilio no sera una Objeto que pueda acceder a Paciente
     * En estricto Sentido
     * Un Turno puede tener N Pacientes + N Odontologos
     *  @JoinColumn, por defecto lo redirecciona al id de la tabala que deseamos realcionar
     *  @JoinColumn, name es opcional coloca por defecto el nombre de la clase + "_" + "id"
     *  @JoinColumn; genra llave foranea
     */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domicilio" , referencedColumnName = "id")
    private Domicilio domicilio;

    /**
     * En estricto Sentido
     * Un Paciente puede tener muchos tuernos asignados <<set>>
     * Donde Turno es la Tabla Padre
     * Donde Paciente es la Tabla hija y hay un atributo de la clase Padre<set> se usa mappedBy
     *  @OneToMany(mappedBy) en Tabla padre existe un atributo del tipo de la tabla Hija
     *  @JsonIgnore, EVITA  el ciclado de objetos JSON
     */

    @OneToMany(mappedBy = "paciente",  cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Turno> turnoSet = new HashSet<>();


}
