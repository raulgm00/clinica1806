package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Especialidad;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface IEspecialidadService {

    Especialidad registrarEspecialidad(Especialidad e) throws BadRequestException;
    Optional<Especialidad> buscarEspecialidadPorId(Integer id) throws ResourcesNotFoundException;
    List<Especialidad> buscarTodosLosEspecialidads() throws ResourcesNotFoundException;
    void actualizarEspecialidad(Especialidad e) throws BadRequestException, ResourcesNotFoundException;
    void eliminarEspecialidad(Integer id) throws ResourcesNotFoundException;


}
