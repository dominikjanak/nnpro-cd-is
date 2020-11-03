package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Area;
import cz.janakdom.backend.model.database.FireBrigadeUnit;
import cz.janakdom.backend.model.database.Railroad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FireBrigadeUnitDao extends JpaRepository<FireBrigadeUnit, Integer> {
    Page<FireBrigadeUnit> findAllByIsDeletedFalse(Pageable pageable);
    Optional<FireBrigadeUnit> findByName(String name);
}
