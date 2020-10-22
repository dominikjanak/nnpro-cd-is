package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Area;
import cz.janakdom.backend.model.database.FireIncident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireIncidentDao extends JpaRepository<FireIncident, Integer> {

}
