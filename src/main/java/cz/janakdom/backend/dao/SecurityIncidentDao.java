package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.SecurityIncident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityIncidentDao extends JpaRepository<SecurityIncident, Integer> {

}
