package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.IPacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /*
    * http://localhost:8080/pacientes
    * */
    @PostMapping
    public ResponseEntity<Paciente>  registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        Paciente pacienteARetornar = pacienteService.registrarPacientes(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);

    }

    /*
    * http://localhost:8080/pacientes
    *
    * */
    @GetMapping
    public ResponseEntity<List<Paciente>>  buscarTodos() throws ResourcesNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    /*
    * http://localhost:8080/pacientes/1
    * */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) throws ResourcesNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteService.buscarPacientesPorId(id);
        return ResponseEntity.ok(pacienteOptional.get());

    }

    /*
    * http://localhost:8080/pacientes
    * */
    @PutMapping(produces = "application/json")
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente) throws ResourcesNotFoundException, BadRequestException {
        pacienteService.actualizarPaciente(paciente);
        return  ResponseEntity.ok("{\"messages\":  \"Paciente actualizado\"}");


    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String>  borrarPaciente(@PathVariable Integer id) throws ResourcesNotFoundException {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("{\"messages\":  \"Paciente eliminado\"}");

    }

    @GetMapping("/dni")
    public ResponseEntity<Paciente> buscarPacientePorDNI(@RequestParam String dni) throws ResourcesNotFoundException, BadRequestException {
            return ResponseEntity.ok(pacienteService.buscarPacienteporDNI(dni));
    }

    @GetMapping("/provincia")
    public ResponseEntity<List<Paciente>> buscarPacientePorProvincia(@RequestParam String provincia) throws ResourcesNotFoundException {
        List<Paciente> listaPacientes = pacienteService.buscarPacienteDomicilioPorProvincia(provincia);
            return ResponseEntity.ok(listaPacientes);
    }

}
