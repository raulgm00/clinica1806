package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {


    /*Inyeccion de dependencias
    * Inyectamos el Service por medio d ela interface
    * */
    private ITurnoService turnoService;

    /*Inyeccion de dependencias a la clase Services*/

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    /* POST*/
    /* http://localhost:8080/turnos */
    @PostMapping(produces = "application/json")
    public ResponseEntity <TurnoResponseDto> registrarTurno(@RequestBody TurnoRequestDto turnoRequestDto) throws BadRequestException {
        TurnoResponseDto turnoResponseDtoADevolver = turnoService.registrarTurno(turnoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDtoADevolver);
    }

    /* GET*/
    /* http://localhost:8080/turnos */
    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosTurnos() throws ResourcesNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnos());
    }

    /* GET */
    /* PATH VARIABLE */
    /* http://localhost:8080/turnos/2 */

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id) throws ResourcesNotFoundException {
        TurnoResponseDto turnoResponseDto = turnoService.buscarTurnoPorId(id);
            return ResponseEntity.ok(turnoResponseDto);

    }

    /* PUT */
    /* ACTUALIZACION TOTAL DTO´s*/
    /* http://localhost:8080/turnos/id */
    @PutMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turnoRequestDto) throws ResourcesNotFoundException, BadRequestException {

        /* Validar exista el turno ants de actualizarlo */
            turnoService.actualizarTurno(id, turnoRequestDto);
            return ResponseEntity.ok("{\"messages\":  \"Turno actualizado\"}");
    }

    /*Deshabiklitados por DTO´s activos */
//    @PutMapping(produces = "application/json")
//    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turnoRequestDto){
//        turnoService.actualizarTurno(id, turnoRequestDto);
//        return ResponseEntity.ok("{\"messages\":  \"Turno actualizado\"}");
//    }


    /*DELETE*/
    /*PATH VARIABLE*/
    /* http://localhost:8080/pacientes/2 */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id) throws ResourcesNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"messages\":  \"Turno eliminado\"}");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnoEntreFechas(@RequestParam String inicio, @RequestParam String fin) throws ResourcesNotFoundException, BadRequestException {
        LocalDate fechaInicio = LocalDate.parse(inicio,formatter);
        LocalDate fechaFin = LocalDate.parse(fin,formatter);
        List<TurnoResponseDto> listaTurnosResponseDto = turnoService.buscarTurnoEntreFechas(fechaInicio,fechaFin);
        return ResponseEntity.ok(listaTurnosResponseDto);
    }


    @GetMapping("/fechasPorterior")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnoPosteriorFechas(@RequestParam String fechaPosterior) throws ResourcesNotFoundException {
        LocalDate fechaPosteriorFormatter = LocalDate.parse(fechaPosterior,formatter);
        List<TurnoResponseDto> listaTurnosResponseDto = turnoService.buscarTurnoPosteriorFecha(fechaPosteriorFormatter);
        return ResponseEntity.ok(listaTurnosResponseDto);

    }


}
