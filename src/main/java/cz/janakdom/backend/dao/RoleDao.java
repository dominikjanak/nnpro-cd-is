package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
