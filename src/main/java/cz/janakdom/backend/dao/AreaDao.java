package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaDao extends JpaRepository<Area, Integer> {

}
