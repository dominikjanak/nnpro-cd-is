package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionDao extends JpaRepository<Region, Integer> {

}
