package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.FireBrigadeUnit;
import cz.janakdom.backend.model.database.PremiseIncident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PremiseIncidentDao extends JpaRepository<PremiseIncident, Integer> {
}
