package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.EPS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EPSDao extends JpaRepository<EPS, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
}
