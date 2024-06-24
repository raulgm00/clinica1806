package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository <Paciente, Integer> {
    //Buscarf paciente por DNI
    @Query("SELECT p FROM Paciente p WHERE LOWER(p.dni) = LOWER(:dni)")
    Paciente findByDNI(@Param("dni") String dni);

    //Buscar paciente por Domicilio Provincia
    @Query("SELECT p FROM Paciente p INNER JOIN Domicilio d WHERE d.provincia= :provincia ")
    List<Paciente> findByProvincia(@Param("provincia") String provincia);

//    @Query("SELECT p FROM Paciente p WHERE LOWER(p.domicilio.provincia) LIKE LOWER(CONCAT('%', :provincia, '%'))")
//    List<Paciente> buscarPorProvinciaLike(@Param("provincia") String provincia);
//

}
