package dh.backend.clinicamvc.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<String> recursoNoEncontrado(ResourcesNotFoundException e){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> recursoMalFormado(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
