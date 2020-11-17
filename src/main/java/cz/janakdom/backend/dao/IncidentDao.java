package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentDao extends JpaRepository<Incident, Integer> {
    // all premise incidents
    List<Incident> findAllByRegionInOrderByCreationDatetimeDesc(List<Region> region);

    // all premise incidents
    List<Incident> findAllByPremiseIncidentIsNotNullAndSecurityIncidentIsNullAndRegionInOrderByCreationDatetimeDesc(List<Region> region);

    // all security incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNullAndRegionInOrderByCreationDatetimeDesc(List<Region> region);

    // all fire incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNullAndRegionInOrderByCreationDatetimeDesc(List<Region> region);

    //all security incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNullAndCreationDatetimeBetween(LocalDateTime start, LocalDateTime end);

    //all fire incidents
    List<Incident> findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNullAndCreationDatetimeBetween(LocalDateTime start, LocalDateTime end);

}

