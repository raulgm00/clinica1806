package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    /* Uso de nomenglatura de JPA*/
    Optional<User> findByEmail(String email);
}
