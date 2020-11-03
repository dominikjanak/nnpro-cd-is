package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionDao extends JpaRepository<Region, Integer> {
    List<Region> findAllByIsDeletedFalse();
    Optional<Region> findByAbbreviation(String abbreviation);
}
