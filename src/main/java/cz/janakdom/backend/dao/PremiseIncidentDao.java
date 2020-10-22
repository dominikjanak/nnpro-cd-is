package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.PremiseIncident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiseIncidentDao extends JpaRepository<PremiseIncident, Integer> {

}
