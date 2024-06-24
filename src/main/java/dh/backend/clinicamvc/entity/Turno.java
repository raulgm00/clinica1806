package dh.backend.clinicamvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name ="turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //IDENTITY: asigna claves primarias para las entidades que utilizan una columna deidentidad, son autoincrementales.
    Integer id;

    /**
     * Muchos Turnos solo pueden tener un pacciente y un Odontologo unicos
     * Un paciente tiene un unico Domicilio
     * Un Odontologo solo tienen sus datos personales
     * En estricto sentido las relacones son
     * Muchos Turnos puede tener 1 Pacientes unico pero si pueden tener muchos pacientes difewrentes
     * Muchos Turnos puede tener 1 Odontologos unico pero si pueden tener muchos pacientes difewrentes
     * Un Paciente pueden estar definidos en N Turno
     * Un Odontologo pueden estar definidos en N Turno
     */
    @ManyToOne
    Paciente paciente;
    @ManyToOne
    Odontologo odontologo;
    LocalDate fecha;
}
