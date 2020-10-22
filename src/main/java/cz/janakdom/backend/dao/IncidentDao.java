package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentDao extends JpaRepository<Incident, Integer> {

}
