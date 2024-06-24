package dh.backend.clinicamvc.service.test;


import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.impl.PacienteService;


import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*Agregamos esta anotacion de SpringBoot Test*/
@SpringBootTest
class PacienteServiceTest {

    public static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);

    /*Inyeccion por Autowire*/
    @Autowired
    private PacienteService pacienteService ;
    private Paciente paciente ;
    /*Ejecutar esto antes que nada*/
    @BeforeEach
    void setUp(){
        /*Creamos Paciente & Domicilio a mano */
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024,01,12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Garza Garcia");
        paciente.setDomicilio(domicilio);
    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado() throws BadRequestException {
        Paciente PacienteDesdeLaBD = pacienteService.registrarPacientes(paciente);

        assertNotNull(PacienteDesdeLaBD);
    }

    @Test
    @DisplayName("Testear que un paciente existe respecto a un ID")
    void testPacientePorID() throws ResourcesNotFoundException {
        Integer id = 1;
        Optional<Paciente> pencontrado = pacienteService.buscarPacientesPorId(id);
        Paciente pacienteEncontrado = pencontrado.get();
        assertEquals(id,pacienteEncontrado.getId());


    }

    @Test
    @DisplayName("Testear busqueda de todos los pacientes")
    void testTodosPacientes() throws ResourcesNotFoundException {
        List<Paciente> listaPacientes = pacienteService.buscarTodosLosPacientes();
        assertTrue(listaPacientes.size() != 0 );
    }

}