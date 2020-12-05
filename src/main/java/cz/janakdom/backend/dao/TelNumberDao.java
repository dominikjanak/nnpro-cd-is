package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.TelNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelNumberDao extends JpaRepository<TelNumber, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
}
