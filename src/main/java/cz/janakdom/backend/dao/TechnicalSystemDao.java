package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.TechnicalSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalSystemDao  extends JpaRepository<TechnicalSystem, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
}
