package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente registrarPacientes(Paciente paciente) throws BadRequestException;

    Optional<Paciente> buscarPacientesPorId(Integer idPaciente) throws ResourcesNotFoundException;

    List<Paciente> buscarTodosLosPacientes() throws ResourcesNotFoundException;

    void actualizarPaciente(Paciente paciente) throws BadRequestException, ResourcesNotFoundException ;
    void eliminarPaciente(Integer id) throws ResourcesNotFoundException;

    //Metodos con HQL - Hibernate - JPA
    Paciente buscarPacienteporDNI(String dni) throws BadRequestException;

    List<Paciente> buscarPacienteDomicilioPorProvincia(String provincia) throws ResourcesNotFoundException;
}
