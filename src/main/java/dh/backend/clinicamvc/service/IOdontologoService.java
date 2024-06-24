package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo o) throws BadRequestException;
    Optional<Odontologo> buscarOdontologoPorId(Integer id) throws ResourcesNotFoundException;
    List<Odontologo> buscarTodosLosOdontologos() throws ResourcesNotFoundException;
    void actualizarOdontologo(Odontologo odontolo) throws BadRequestException, ResourcesNotFoundException ;
    void eliminarOdontologo(Integer id) throws ResourcesNotFoundException;

    //Metodos con HQL - Hibernate - JPA
    List<Odontologo> buscarPorApellido (String apellido) throws ResourcesNotFoundException;
    List<Odontologo> bucarPorNombre (String nombre) throws ResourcesNotFoundException;

    //Agregar Especialidades al Odontologo

    Odontologo agregarEspecialidad(Integer id_odontologo, Integer id_especialidad) throws ResourcesNotFoundException, BadRequestException;
}
