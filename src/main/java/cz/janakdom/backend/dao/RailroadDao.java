package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Railroad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RailroadDao extends JpaRepository<Railroad, Integer> {

}
