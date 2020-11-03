package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaDao extends JpaRepository<Area, Integer> {
    List<Area> findAllByIsDeletedFalse();
}
