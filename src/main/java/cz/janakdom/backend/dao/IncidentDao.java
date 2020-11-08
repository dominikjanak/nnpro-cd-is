package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentDao extends JpaRepository<Incident, Integer> {
    List<Incident> findAllByPremiseIncidentIsNotNullAndSecurityIncidentIsNull(); // all premise incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNull(); // all security incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNull(); // all fire incidents
}
