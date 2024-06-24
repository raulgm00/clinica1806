package dh.backend.clinicamvc.service.test;


import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dh.backend.clinicamvc.service.impl.OdontologoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*Agregamos esta anotacion de SpringBoot Test*/
@SpringBootTest
class OdontologoServiceTest {

    public static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);

    /*Inyeccion por Autowire*/
    @Autowired
    private OdontologoService odontologoService ;
    private Odontologo odontologo ;
    /*Ejecutar esto antes que nada*/
    @BeforeEach
    void setUp(){
        /*Creamos Odontolo a mano */

        odontologo = new Odontologo();
        odontologo.setId(1);
        odontologo.setNombre("Raul");
        odontologo.setApellido("Garcia");

    }

    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado() throws BadRequestException {
        Odontologo odontologoDesdeLaBD = odontologoService.registrarOdontologo(odontologo);
        assertNotNull(odontologoDesdeLaBD);
    }

    @Test
    @DisplayName("Testear que un odontologo existe respecto a un ID")
    void testOdontologoPorID() throws ResourcesNotFoundException {
        Integer id = 1;
        Optional<Odontologo> odontologEncontrado = odontologoService.buscarOdontologoPorId(id);
        Odontologo odontologoEncontrado = odontologEncontrado.get();
        assertEquals(id,odontologoEncontrado.getId());


    }

    @Test
    @DisplayName("Testear busqueda de todos los odontologos")
    void testTodosOdontologos() throws ResourcesNotFoundException {
        List<Odontologo> listaOdontologos = odontologoService.buscarTodosLosOdontologos();
        assertTrue(listaOdontologos.size() != 0 );
    }

}