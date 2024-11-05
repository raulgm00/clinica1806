package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.IOdontologoService;

import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    /*
     * http://localhost:8080/odontologos
     * */
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        Odontologo OdontologoARetornar = odontologoService.registrarOdontologo(odontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(OdontologoARetornar);
    }

    /*
     * http://localhost:8080/odontologos
     * */
    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos() throws ResourcesNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarTodosLosOdontologos());
    }


    /*
     * http://localhost:8080/odontologos/3
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id) throws ResourcesNotFoundException {
        Optional<Odontologo> optionalOdontologoARetornar = odontologoService.buscarOdontologoPorId(id);
        return ResponseEntity.ok(optionalOdontologoARetornar.get());
    }

    /*
     * http://localhost:8080/odontologos
     * */
    @PutMapping(produces = "application/json")
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo Odontologo) throws ResourcesNotFoundException, BadRequestException {
        odontologoService.actualizarOdontologo(Odontologo);
        return ResponseEntity.ok("{\"messages\":  \"Odontologo actualizado\"}");
    }

    /*
     * http://localhost:8080/odontologos/3
     * */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> borrarOdontologo(@PathVariable Integer id) throws ResourcesNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"messages\":  \"Odontologo eliminado\"}");

    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorApellido(@PathVariable String apellido) throws ResourcesNotFoundException {
        List<Odontologo> listaOdontologos = odontologoService.buscarPorApellido(apellido);
        return ResponseEntity.ok(listaOdontologos);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorNombre(@PathVariable String nombre) throws ResourcesNotFoundException {
        List<Odontologo> listaOdontologos = odontologoService.bucarPorNombre(nombre);
        return ResponseEntity.ok(listaOdontologos);
    }

    @PutMapping("/{id_odontologo}/especialidad/{id_especialidad}")
    public ResponseEntity<Odontologo> agregarEspecialidadAOdontologo(@PathVariable Integer id_odontologo,
                                                                     @PathVariable Integer id_especialidad) throws ResourcesNotFoundException, BadRequestException {
        return ResponseEntity.ok(odontologoService.agregarEspecialidad(id_odontologo, id_especialidad));
    }


}
