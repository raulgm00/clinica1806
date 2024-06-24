package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private IPacienteRepository pacienteRepository;
    private IOdontologoRepository odontologoRepository;
    private ITurnoRepository turnoRepository;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    /*Objeto de mapeo*/
    private ModelMapper modelMapper;

    public TurnoService(IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> p = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        LOGGER.info("Paciente -  "+ p.isPresent() );
        Optional<Odontologo> o = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        LOGGER.info("Odontologo -  "+ o.isPresent()  );
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if(p.isPresent() && o.isPresent()){
            turnoARegistrar.setPaciente(p.get());
            turnoARegistrar.setOdontologo(o.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            turnoADevolver = mapToResponseDTO(turnoGuardado);

            //Armar el turno a devolver
            // Conformado por PacienteResponseDto & OdontologoResponseDto

//            PacienteResponseDto pacienteResponseDTO= new PacienteResponseDto();
//            pacienteResponseDTO.setId(turnoGuardado.getPaciente().getId());
//            pacienteResponseDTO.setNombre(turnoGuardado.getPaciente().getNombre());
//            pacienteResponseDTO.setApellido(turnoGuardado.getPaciente().getApellido());
//            pacienteResponseDTO.setDni(turnoGuardado.getPaciente().getDni());
//
//            OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto();
//            odontologoResponseDto.setId(turnoGuardado.getOdontologo().getId());
//            odontologoResponseDto.setNumeroMatricula(turnoGuardado.getOdontologo().getNumeroMatricula());
//            odontologoResponseDto.setNombre(turnoGuardado.getOdontologo().getNombre());
//            odontologoResponseDto.setApellido(turnoGuardado.getOdontologo().getApellido());

            //Objeto creado
//            turnoADevolver = new TurnoResponseDto();
//            turnoADevolver.setId(turnoGuardado.getId());
//            turnoADevolver.setPaciente(pacienteResponseDTO);
//            turnoADevolver.setOdontologo(odontologoResponseDto);
//            turnoADevolver.setFecha(String.valueOf(turnoGuardado.getFecha()));
//
//            turnoADevolver = new TurnoResponseDto();
//            turnoADevolver.setId(turnoGuardado.getId());
//            turnoADevolver.setPaciente(pacienteResponseDTO);
//            turnoADevolver.setOdontologo(odontologoResponseDto);
//            turnoADevolver.setFecha(String.valueOf(turnoGuardado.getFecha()));
            LOGGER.info("Turno registrado: " + turnoADevolver);
            return turnoADevolver;
        }else{
            LOGGER.info("El Turno no se puede registrar por que el paciente :" +  p.isPresent() + " , odontologo : " +o.isPresent() );
            throw new BadRequestException("{\"messages\":  \"El paciente u odontologo no existen\"}");
        }



    }

    @Override
    public TurnoResponseDto buscarTurnoPorId(Integer idTurno) throws ResourcesNotFoundException {
        Optional<Turno> turnoEncontradoOptional = turnoRepository.findById(idTurno);
        /*Validar si existe el turno bajo la API de JPA*/
        if(turnoEncontradoOptional.isPresent()){
            TurnoResponseDto turnoADevolver = mapToResponseDTO(turnoEncontradoOptional.get());
            LOGGER.info("Turno encontrado por id: " + turnoADevolver);
            return turnoADevolver;
        }else{
            LOGGER.info("Busqueda de Turno por id : no exitoso" );
            throw new ResourcesNotFoundException("{\"messages\":  \"Turno no encontrado\"}");
        }

    }

    @Override
    public List<TurnoResponseDto> buscarTodosLosTurnos() throws ResourcesNotFoundException {
        List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
        List<Turno> listaTurnosADevolver = turnoRepository.findAll();

        if(listaTurnosADevolver.size() > 0){
            TurnoResponseDto turnoADevolver = null;
            for (Turno t: listaTurnosADevolver) {
                turnoADevolver = mapToResponseDTO(t);
                listaTurnosResponseDto.add(turnoADevolver);
            }

            LOGGER.info("Lista de turnos generada: " + listaTurnosResponseDto);
            return listaTurnosResponseDto;
        }else{
            LOGGER.info("Lista de turnos no generada: " + listaTurnosResponseDto);
            throw new ResourcesNotFoundException ("{\"messages\":  \"Turnos no encontrados\"}");
        }

    }

    @Override
    public void actualizarTurno(Integer idTurno,TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourcesNotFoundException {


        Integer idPaciente = turnoRequestDto.getPaciente_id();
        Integer idOdontologo = turnoRequestDto.getOdontologo_id();

        if( idTurno != null || idPaciente != null || idOdontologo != null){

            /*Buscando dependencias bajo el Framework de Hibernate - JPA*/
            Optional<Paciente> p = pacienteRepository.findById(idPaciente);
            Optional<Odontologo> o = odontologoRepository.findById(idOdontologo);


            if(p.isPresent() && o.isPresent()){

                /*Solo para saber si existe , si existe lo modifica si no no lo modifica*/
                Optional<Turno> turnoABuscarPorId = turnoRepository.findById(idTurno);

                LOGGER.info("Paciente encontrado :" +  (p.isPresent() ? true : false) );
                LOGGER.info("Odontologo encontrado :" +  (o.isPresent() ? true : false) );
                LOGGER.info("Turno encontrado :" +  (turnoABuscarPorId.isPresent() ? true : false) );


                Turno turnoAModificar = new Turno();
                    turnoAModificar.setId(idTurno);
                    turnoAModificar.setPaciente(p.get());
                    turnoAModificar.setOdontologo(o.get());
                    turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
                    LOGGER.info("Turno a modificar "+ turnoAModificar);
                    turnoRepository.save(turnoAModificar);

            }else{
                LOGGER.info("Turno no actualziado : " );
                throw new ResourcesNotFoundException("{\"messages\":  \"Paciente o Odontologo no encontrado para actualziar dentro d elos Turnos\"}");
            }





        }else{
            LOGGER.info("Turno a modificar "+ turnoRequestDto + " no encontrado");
            throw new BadRequestException("{\"messages\":  \"Turno con estructura de objeto erronea, comprueba los datos e intentalo de nuevo\"}");
        }



    }

    @Override
    public void eliminarTurno(Integer id) throws ResourcesNotFoundException {
        Optional<Turno> optionalTurno = turnoRepository.findById(id);

        if(optionalTurno.isPresent()){
            LOGGER.info("Borrando Turno con id: " + id  );
            turnoRepository.deleteById(id);
        }else{
            throw  new ResourcesNotFoundException("{\"messages\":  \"Turno no encontrado\"}");
        }


    }


    /**
     * Busqueda de metodos HQL
     */

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) throws BadRequestException {
        List<Turno> listaTurnos = turnoRepository.buscarTurnoEntreFechas(startDate,endDate);

        if(listaTurnos.size() > 0 ){
            List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
            TurnoResponseDto turnoAuxiliar = null;
            for (Turno t: listaTurnos) {
                turnoAuxiliar = mapToResponseDTO(t);
                listaTurnosResponseDto.add(turnoAuxiliar);
            }
            LOGGER.info("Tamaño de la lista de turnos reponse dto´s "+ listaTurnosResponseDto.size());
            return listaTurnosResponseDto;
        }else{
            LOGGER.info("Tamaño de la lista de turnos reponse dto´s "+ listaTurnos.size());
            throw new BadRequestException ("{\"messages\":  \"No se encontraron Turnos para el rango de fechas definido \"}");
        }

    }

    @Override
    public List<TurnoResponseDto> buscarTurnoPosteriorFecha(LocalDate startDate) throws ResourcesNotFoundException {
        List<Turno> listaTurnoPosteriorFecha = turnoRepository.findByStartDateTurnosAfter(startDate);

        if(listaTurnoPosteriorFecha.size() > 0){
            List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
            for (Turno t: listaTurnoPosteriorFecha) {
                listaTurnosResponseDto.add( mapToResponseDTO(t));
            }
            LOGGER.info("Tamaño de la lista de turnos posteriores a la fecha "+ startDate + " " + listaTurnosResponseDto.size());
            return listaTurnosResponseDto;
        }else{
            LOGGER.info("La fecha "+ startDate + " no tienen Turnos que mostrar" );
            throw new ResourcesNotFoundException("{\"messages\":  \"Turnos no encontrados porapra la fecha definida\"}");
        }

    }


    /*Metodo de mapeo*/
    private TurnoResponseDto mapToResponseDTO( Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        LOGGER.info("Mapeando tipos de datos: " + turnoResponseDto);
        return turnoResponseDto;
    }



}
