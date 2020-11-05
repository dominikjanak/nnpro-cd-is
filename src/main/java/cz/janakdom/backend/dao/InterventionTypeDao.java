package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.InterventionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterventionTypeDao extends JpaRepository<InterventionType, Integer> {
    List<InterventionType> findAllByIsDeletedFalse();
    Optional<InterventionType> findByName(String name);
}
