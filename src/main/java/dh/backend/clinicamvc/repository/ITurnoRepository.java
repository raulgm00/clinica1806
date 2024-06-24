package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
    //Buscar entre dos fechas
    @Query("SELECT t FROM Turno t WHERE t.fecha BETWEEN :startDate and :endDate")
    List<Turno> buscarTurnoEntreFechas(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);

    //Buscar todos los turnos posteriores a la fecha actual.
    @Query("SELECT t FROM Turno t WHERE :startDate > CURRENT_DATE")
    List<Turno> findByStartDateTurnosAfter(@Param("startDate")LocalDate startDate);

}
