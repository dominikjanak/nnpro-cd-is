package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDao extends JpaRepository<FileDB, Integer> {
    void deleteAllByBuildingId(Integer buildingId);
    List<FileDB> findAllByBuildingId(Integer building_id);
}
