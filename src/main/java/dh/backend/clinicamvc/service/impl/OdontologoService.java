package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Especialidad;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.repository.IEspecialidadesRepository;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IEspecialidadService;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/*Inteccion de dependencias con Service*/
@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;
    private IEspecialidadService especialidadService;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);

    /**
     * Inyeccion de dependencias por constructor
     * Se pued einyectar tambien el Repository como el Service
     */



    public OdontologoService(IOdontologoRepository odontologoRepository, IEspecialidadService especialidadService) {
        this.odontologoRepository = odontologoRepository;
        this.especialidadService = especialidadService;
    }

    @Override
    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException {

        if(odontologo.getNombre() != null && odontologo.getApellido() != null){
            LOGGER.info("Registrando : " + odontologo );
            Odontologo o = odontologoRepository.save(odontologo);
            return o;
        }else{
            LOGGER.info("Odontologo no (Guardado) : " + odontologo );
            throw new BadRequestException("{\"messages\":  \"Odontologo no registrado\"}");
        }

    }

    @Override
    public Optional<Odontologo> buscarOdontologoPorId(Integer id) throws ResourcesNotFoundException{
        // Optional, tipo de dato que guarda un dato si se que lo encuentra y un null su no lo encuentra
        Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);
        if(optionalOdontologo.isPresent()) {
            LOGGER.info("Odontologo encontrado con id : " + id);
            return optionalOdontologo;
        }else{
            LOGGER.info("Odontologo no encontrado con id : " + id);
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologo no encontrado\"}");
        }
    }

    @Override
    public List<Odontologo> buscarTodosLosOdontologos() throws ResourcesNotFoundException{

        List<Odontologo> listaOdontologosARetornar = odontologoRepository.findAll();

        if(listaOdontologosARetornar.size() > 0){
            LOGGER.info("Lista de Odontologos encontrados : " + listaOdontologosARetornar.size());
            return listaOdontologosARetornar;
        }else{
            LOGGER.info("No se encontro Odontologos en la BD" );
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologos no encontrados\"}");
        }


    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) throws BadRequestException, ResourcesNotFoundException{
        /* Aqui se pisa el objeto actual, por detras JPA lo pisa y lo actualiza completamente */

        if( odontologo.getId()!= null && odontologo.getNombre() != null && odontologo.getApellido() != null) {


            Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(odontologo.getId());
            if (optionalOdontologo.isPresent()) {
                LOGGER.info("Actualizando : " + odontologo.getId() );
                odontologoRepository.save(odontologo);

            } else {
                LOGGER.info("Odocntologo no actualziado : " + odontologo.getNombre() );
                throw new ResourcesNotFoundException("{\"messages\":  \"Odontologo no encontrado para actualizar\"}");
            }
        }else{
            LOGGER.info("Odontologo no actualziado : " + odontologo.getNombre() );
            throw new BadRequestException("{\"messages\":  \"Odontologo con estructura de objeto erronea, comprueba los datos e intentalo de nuevo\"}");
        }


    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourcesNotFoundException {
        Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);

        if(optionalOdontologo.isPresent()){
            LOGGER.info("Borrando Odontologo con id: " + id  );
            odontologoRepository.deleteById(id);
        }else{
            LOGGER.info("No se encontro Odontologo con id: " + id  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologo no encontrado\"}");
        }

    }

    /**
     * Busqueda de metodos HQL
     */
    @Override
    public List<Odontologo> buscarPorApellido(String apellido) throws ResourcesNotFoundException{
        List<Odontologo> listaOdontologosARetornarPorApellido = odontologoRepository.buscarPorApellido(apellido);

        if( listaOdontologosARetornarPorApellido.size() > 0 ){
            LOGGER.info("Buscando Odontolo por apellido : " + apellido  );
            return listaOdontologosARetornarPorApellido;
        }else{
            LOGGER.info("No se encontraron Odontologos con ese apellido : " + apellido  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologos no encontrados por apellido \"}");
        }


    }

    @Override
    public List<Odontologo> bucarPorNombre(String nombre) throws ResourcesNotFoundException {
        List<Odontologo> listaOdontologosARetornarPorNombre = odontologoRepository.findByNombreLike(nombre);

        if( listaOdontologosARetornarPorNombre.size() > 0 ) {
            LOGGER.info("Buscnado Odontolo por nombre : " + nombre);
            return listaOdontologosARetornarPorNombre;
        }else{
            LOGGER.info("No se encontraron Odontologos con ese nombre : " + nombre  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Odontologos no encontrados por nombre\"}");

        }
    }

    @Override
    public Odontologo agregarEspecialidad(Integer id_odontologo, Integer id_especialidad) throws ResourcesNotFoundException, BadRequestException {

        Optional<Odontologo> optionalOdontologo = buscarOdontologoPorId(id_odontologo);

        if(optionalOdontologo.isEmpty()) throw new ResourcesNotFoundException("{\"messages\":  \"Odontologos no encontrado\"}");

        Optional<Especialidad> optionalEspecialidad = especialidadService.buscarEspecialidadPorId(id_especialidad);

        if(optionalEspecialidad.isEmpty()) throw new ResourcesNotFoundException("{\"messages\":  \"Especialidad no encontrada \"}");

        Odontologo odontologoARetornar = optionalOdontologo.get();
        LOGGER.info("Odontologo a modificar : " + odontologoARetornar.getNombre() + " " + odontologoARetornar.getApellido() );
        odontologoARetornar.getEspecialidades().add(optionalEspecialidad.get());
        actualizarOdontologo(odontologoARetornar);

        return odontologoARetornar;

    }
}
