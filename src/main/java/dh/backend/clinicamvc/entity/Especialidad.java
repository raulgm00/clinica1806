package dh.backend.clinicamvc.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SecondaryRow;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "especialidades")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;

    /**
     * Una especialidad puede estar en varios odontologos
     * Tabla no dueña de la relacion
     * mappedBy = se denota la conexion de la tabla padres, existe un atributo llamado asi
     * JsonIgnore = se coloca en la tabla hija para evitar recursividad
     * JsonIgnore = Yo no necesito ver las especialdiades Vs los odnotloodos
     * JsonIgnore = Pero si necesito los odontologos ocn sus especialidades
     * Set = conjunto de odontologos
     * mappedBy && JsonIgnore, van en las tablas que nos son principales
     *
     *
     */

    @ManyToMany ( mappedBy = "especialidades")
    @JsonIgnore
    Set<Odontologo> odontologos = new HashSet<>();

}
