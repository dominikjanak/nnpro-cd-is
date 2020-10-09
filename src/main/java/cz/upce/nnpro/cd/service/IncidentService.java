package cz.upce.nnpro.cd.service;

import cz.upce.nnpro.cd.dao.IncidentRepository;
import cz.upce.nnpro.cd.model.database.Incident;
import cz.upce.nnpro.cd.model.dto.NewIncident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public Incident getById(int incidentId) {
        var incident = incidentRepository.findById(incidentId);

        if (incident.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return incident.get();
    }

    public Page<Incident> getAll(Pageable pageable, String filter) {
        return incidentRepository.findAllByNameContainingIgnoreCase(pageable, filter);
    }

    public Incident add(NewIncident newIncident) {
        Incident incident = new Incident(newIncident);
        return incidentRepository.save(incident);
    }

    public void remove(int incidentId) {
        incidentRepository.deleteById(incidentId);
    }

    public Incident update(int incidentId, NewIncident incident) {
        // TODO
        return null;
    }

}
