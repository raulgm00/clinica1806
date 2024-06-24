package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/* Clase pasamanos */
public interface ITurnoService {
    TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException;

    TurnoResponseDto buscarTurnoPorId(Integer idTurno) throws ResourcesNotFoundException;

    List<TurnoResponseDto> buscarTodosLosTurnos() throws ResourcesNotFoundException;

    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourcesNotFoundException ;
    void eliminarTurno(Integer id) throws ResourcesNotFoundException;

    //Metodos con HQL - Hibernate - JPA
    List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) throws ResourcesNotFoundException, BadRequestException;
    List<TurnoResponseDto> buscarTurnoPosteriorFecha(LocalDate startDate) throws ResourcesNotFoundException;


}
