package ru.specialist.java.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.specialist.java.spring.entity.Role;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
