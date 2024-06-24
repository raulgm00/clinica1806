package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/*Inteccion de dependencias con Service*/
@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    /*Inyeccion de dependencias por constructor*/
    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente registrarPacientes(Paciente paciente) throws BadRequestException {
        if (paciente.getNombre() != null && paciente.getApellido() != null){
            Paciente p = pacienteRepository.save(paciente);
            LOGGER.info("Registrando paciente : " + paciente );
            return p;
        }else{
            LOGGER.info("Paciente NO (Guardado) : " + paciente );
            throw new BadRequestException ("{\"messages\":  \"Paciente no registrado\"}");
        }

    }

    @Override
    public Optional<Paciente> buscarPacientesPorId(Integer idPaciente) throws ResourcesNotFoundException {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(idPaciente);
        if(optionalPaciente.isPresent()) {
            LOGGER.info("Busqueda de Paciente por id : " + idPaciente);
            return optionalPaciente;
        }else{
            LOGGER.info("Busqueda de Paciente por id : no exitoso" );
            throw new ResourcesNotFoundException("{\"messages\":  \"Paciente no encontrado\"}");
        }

    }

    @Override
    public List<Paciente> buscarTodosLosPacientes() throws ResourcesNotFoundException {
        List<Paciente> listaPacientesARetornar = pacienteRepository.findAll();
        if(listaPacientesARetornar.size() > 0 ) {
            LOGGER.info("Lista de Pacientes encontrados : " + listaPacientesARetornar.size());
            return listaPacientesARetornar;
        }else{
            LOGGER.info("Lista de Pacientes encontrados : " + listaPacientesARetornar.size());
            throw new ResourcesNotFoundException ("{\"messages\":  \"Pacientes no encontrados\"}");
        }
    }

    @Override
    public void actualizarPaciente(Paciente paciente) throws BadRequestException, ResourcesNotFoundException {
        /* Aqui se pisa el objeto actual, por detras JPA lo pisa y lo actualiza completamente */

        if( paciente.getId()!= null && paciente.getNombre() != null && paciente.getApellido() != null) {

            Optional<Paciente> optionalPaciente = pacienteRepository.findById( paciente.getId());
            if(optionalPaciente.isPresent()) {
                LOGGER.info("Actualizando : " + paciente );
                pacienteRepository.save(paciente);
            }else {
                LOGGER.info("Paciente no actualziado : " + paciente );
                throw new ResourcesNotFoundException("{\"messages\":  \"Paciente no encontrado para actualziar\"}");
            }


        }else{
            LOGGER.info("Odontologo no actualziado : " + paciente );
            throw new BadRequestException("{\"messages\":  \"Paciente con estructura de objeto erronea, comprueba los datos e intentalo de nuevo\"}");
        }


    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourcesNotFoundException {

        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if( optionalPaciente.isPresent()){
            LOGGER.info("Borrando Paciente con id: " + id  );
            pacienteRepository.deleteById(id);
        }else{
            LOGGER.info("Borrando Paciente no fue encontrado con id: " + id  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Paciente no encontrado\"}");
        }

    }

    /**
     * Busqueda de metodos HQL
     */
    @Override
    public Paciente buscarPacienteporDNI(String dni) throws BadRequestException {
        LOGGER.info("Buscar Paciente por DNI: " + dni  );
        Paciente p = pacienteRepository.findByDNI(dni);

        if(p != null){
            LOGGER.info("Paciente por dni: " + dni + " encontrado" );
            return pacienteRepository.findByDNI(dni);
        }else{
            LOGGER.info("Paciente por dni: " + dni + " no encontrado" );
            throw new BadRequestException ("{\"messages\":  \"DNI de paciente no encontrado\"}");

        }

    }

    @Override
    public List<Paciente> buscarPacienteDomicilioPorProvincia(String provincia) throws ResourcesNotFoundException {
        List<Paciente> listaPacientesARetornar = pacienteRepository.findByProvincia(provincia);
        if( listaPacientesARetornar.size() > 0 ){
            LOGGER.info("Borrando Paciente por provincia: " + provincia  );
            return listaPacientesARetornar;
        }else{
            LOGGER.info("Paciente no encontrado para la  provincia: " + provincia  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Paciente no encontrados por provincia\"}");

        }

    }


}
