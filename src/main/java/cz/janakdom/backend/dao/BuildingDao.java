package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Building;
import cz.janakdom.backend.model.database.Carriage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingDao extends JpaRepository<Building, Integer> {
    Optional<Building> findByInnerno(String innerno);
    List<Building> findAllByIsDeletedFalse();
}
