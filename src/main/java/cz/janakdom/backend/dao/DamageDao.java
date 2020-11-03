package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Damage;
import cz.janakdom.backend.model.database.FireBrigadeUnit;
import cz.janakdom.backend.model.database.FireIncident;
import cz.janakdom.backend.model.database.SecurityIncident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DamageDao extends JpaRepository<Damage, Integer> {
    List<Damage> findAllByIsDeletedFalseAndFireIncidentIsNotNullAndFireIncident_Id(Integer fireIncident_id);
    List<Damage> findAllByIsDeletedFalseAndSecurityIncidentIsNotNullAndSecurityIncident_Id(Integer securityIncident_id);
}
