package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Especialidad;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.repository.IEspecialidadesRepository;
import dh.backend.clinicamvc.service.IEspecialidadService;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService implements IEspecialidadService {

    private IEspecialidadesRepository especialidadRepository;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EspecialidadService.class);

    public EspecialidadService(IEspecialidadesRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    public Especialidad registrarEspecialidad(Especialidad e) throws BadRequestException {
        if( e.getTipo() != null) {
            LOGGER.info("Especialidad registrada : " + e );
            return especialidadRepository.save(e);
        }else{
            throw new BadRequestException("{\"messages\":  \"Especialidad no registrada\"}");
        }
    }

    @Override
    public Optional<Especialidad> buscarEspecialidadPorId(Integer id) throws ResourcesNotFoundException {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id);

        if(optionalEspecialidad.isPresent()){
            LOGGER.info("Especialidad encontrada con id : " + id);
            return optionalEspecialidad;
        }else{
            LOGGER.info("Especialidad no encontrada con id : " + id);
            throw new ResourcesNotFoundException("{\"messages\":  \"Especialidad no encontrada\"}");
        }

    }

    @Override
    public List<Especialidad> buscarTodosLosEspecialidads() throws ResourcesNotFoundException {
        List<Especialidad> listaEspecialidades = especialidadRepository.findAll();
        if(listaEspecialidades.size() > 0){
            LOGGER.info("Lista de Especialidades encontradas : " + listaEspecialidades.size());
            return listaEspecialidades;
        }else{
            LOGGER.info("No se encintraron especialidades en BD");
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologos no encontrados\"}");
        }

    }

    @Override
    public void actualizarEspecialidad(Especialidad e) throws BadRequestException, ResourcesNotFoundException {

        if(e.getTipo() != null && e.getTipo() != null){
            Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(e.getId());
            if (optionalEspecialidad.isPresent()) {
                LOGGER.info("Actualizando : " + e);
                especialidadRepository.save(e);

            } else {
                LOGGER.info("Especialidad no actualziada : " + e );
                throw new ResourcesNotFoundException("{\"messages\":  \"Especialidad no encontrado para actualizar\"}");
            }

        }else{
            LOGGER.info("Especialidad no actualizada : " + e );
            throw new BadRequestException("{\"messages\":  \"Especialidad con estructura de objeto erronea, comprueba los datos e intentalo de nuevo\"}");
        }
    }

    @Override
    public void eliminarEspecialidad(Integer id) throws ResourcesNotFoundException {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id);

        if(optionalEspecialidad.isPresent()){
            LOGGER.info("Borrando Especialidad con id: " + id  );
            especialidadRepository.deleteById(id);
        }else{
            LOGGER.info("No se encontro Especialidad con id: " + id  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Especialidad no encontrada\"}");
        }
    }


}
