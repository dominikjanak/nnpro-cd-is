package cz.upce.nnpro.cd.dao;

import cz.upce.nnpro.cd.model.database.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer> {

    Page<Incident> findAllByNameContainingIgnoreCase(Pageable pageable, String filter);

}