package dh.backend.clinicamvc.dto.request;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TurnoRequestDto {
    Integer paciente_id;
    Integer odontologo_id;
    String fecha;
}
