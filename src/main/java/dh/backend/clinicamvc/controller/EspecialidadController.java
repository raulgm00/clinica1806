package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Especialidad;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.IEspecialidadService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    private IEspecialidadService especialidadService;

    public EspecialidadController(IEspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping
    public ResponseEntity<Especialidad> agregarUnaEspecialdiad(@RequestBody Especialidad especialidad) throws BadRequestException {

        return ResponseEntity.ok(especialidadService.registrarEspecialidad(especialidad));

    }

    @GetMapping
    public  ResponseEntity<List<Especialidad>> buscarTodasLasEspecialidades() throws ResourcesNotFoundException {
        return ResponseEntity.ok(especialidadService.buscarTodosLosEspecialidads());

    }




}
