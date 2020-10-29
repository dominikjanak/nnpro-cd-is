package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.InterventionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterventionTypeDao extends JpaRepository<InterventionType, Integer> {
    Page<InterventionType> findAllByIsDeletedFalse(Pageable pageable);
    Optional<InterventionType> findByName(String name);
}
