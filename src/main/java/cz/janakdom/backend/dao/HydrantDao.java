package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Hydrant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydrantDao extends JpaRepository<Hydrant, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
}
