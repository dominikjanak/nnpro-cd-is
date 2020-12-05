package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.FireExtinguisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireExtinguisherDao  extends JpaRepository<FireExtinguisher, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
}
