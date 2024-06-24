package dh.backend.clinicamvc.service.test;


import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import dh.backend.clinicamvc.service.impl.PacienteService;
import dh.backend.clinicamvc.service.impl.TurnoService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/*Agregamos esta anotacion de SpringBoot Test*/
@SpringBootTest
class TurnoServiceTest {

    public static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);

    /*Inyeccion por Autowire*/
    @Autowired
    private PacienteService pacienteService ;
    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private TurnoService turnoService ;
    private Turno turno ;
    private Paciente paciente;
    private Domicilio domicilio;
    private Odontologo odontologo;

    private TurnoRequestDto turnoDTO;
    /*Objeto de mapeo*/
    private ModelMapper modelMapper;


    /*Ejecutar esto antes que nada*/
    @BeforeEach
    void setUp(){
        /*Creamos Turno & Domicilio a mano */
        turno = new Turno();
        turno.setId(1);

        paciente = new Paciente();
        //paciente.setId(1);
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024,01,12));

        domicilio = new Domicilio();
        //domicilio.setId(1);
        domicilio.setCalle("Calle falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Garza Garcia");
        paciente.setDomicilio(domicilio);

        odontologo = new Odontologo();
       //odontologo.setId(1);
        odontologo.setNumeroMatricula(12345);
        odontologo.setNombre("Roberto");
        odontologo.setApellido("Palasuelos");

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(LocalDate.of(2024,10,26));



    }

    @Test
    @DisplayName("Testear que un turno fue creado")
    void testTurnoGuardado() throws BadRequestException {
        Paciente pacienteDesdeLaBD = pacienteService.registrarPacientes(paciente);
        Odontologo odontologoDesdeBD = odontologoService.registrarOdontologo(odontologo);
        turnoDTO = new TurnoRequestDto(paciente.getId(), odontologo.getId(),"2024-10-26");

        TurnoResponseDto turnoDesdeLaBD = turnoService.registrarTurno(turnoDTO);
        assertNotNull(turnoDesdeLaBD);
    }

    @Test
    @DisplayName("Testear que un turno existe respecto a un ID")
    void testTurnoPorID(){
        Integer id = 1;
        TurnoResponseDto turnoEncontrado = turnoService.buscarTurnoPorId(id);
        assertEquals(id,turnoEncontrado.getId());


    }

    @Test
    @DisplayName("Testear busqueda de todos los pacientes")
    void testTodosTurnos(){
        List<TurnoResponseDto> listaTurnos = turnoService.buscarTodosLosTurnos();
        assertTrue(listaTurnos.size() != 0 );
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