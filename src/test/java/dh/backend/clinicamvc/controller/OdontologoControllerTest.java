package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.service.IOdontologoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/*Indica que la clase es de testeo*/
@SpringBootTest
/*Configuracion del MockMvv*/
@AutoConfigureMockMvc
class OdontologoControllerTest {

    /*Inyectamos las dependencias por anotacion*/
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IOdontologoService odontologoService;

    private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwic3ViIjoicmdhcmNpYUBjbGluaWNhLmNvbSIsImlhdCI6MTczMDc3MjkzMSwiZXhwIjoxNzMwODU5MzMxfQ.XTcBl_IEp2usUMP5GkjSfZis6OLRiLHglQyai5BSB04";

    @Test
    @DisplayName("Testear la obtencion de un odontologo")
    void obtenerOdontologo() throws Exception {
        mockMvc.perform(get("/odontologos/1")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath(("$.numeroMatricula")).value( 444))
                        .andExpect(jsonPath(("$.nombre")).value("Raul"))
                        .andExpect(jsonPath(("$.apellido")).value("Garcia"));
    }

    @Test
    @DisplayName("Testear que un odontologo no existente")
    void noObtenerOdontologo() throws Exception {
        mockMvc.perform(get("/odontologos/12")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testear que se agrego un odontologo a la base de datos")
    void registrarOdontologo() throws Exception {
        String odontologo = "{\"nombre\":\"Juan\",\"apellido\":\"Perez\",\"numeroMatricula\":\"1234\"}";

        mockMvc.perform(post("/odontologos")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologo))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath(("$.nombre")).value("Juan"))
                        .andExpect(jsonPath(("$.apellido")).value("Perez"))
                        .andExpect(jsonPath(("$.numeroMatricula")).value( 1234));

    }
}