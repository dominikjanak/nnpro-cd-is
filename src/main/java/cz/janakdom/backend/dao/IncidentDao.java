package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentDao extends JpaRepository<Incident, Integer> {
    List<Incident> findAllByIsDeletedIsFalse(); // all incidents
    List<Incident> findAllByIsDeletedIsFalseAndPremiseIncidentIsNotNullAndSecurityIncidentIsNull(); // all premise incidents
    List<Incident> findAllByIsDeletedIsFalseAndSecurityIncidentIsNotNullAndPremiseIncidentIsNull(); // all security incidents
    List<Incident> findAllByIsDeletedIsFalseAndSecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNull(); // all fire incidents
    List<Incident> findAllByOwner(User owner);
}
