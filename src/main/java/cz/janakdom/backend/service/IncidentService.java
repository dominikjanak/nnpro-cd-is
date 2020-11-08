package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.IncidentDao;
import cz.janakdom.backend.model.database.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "incidentService")
public class IncidentService {

    @Autowired
    private IncidentDao incidentDao;
    @Autowired
    private PremiseIncidentService premiseIncidentService;
    @Autowired
    private SecurityIncidentService securityIncidentService;

    public List<Incident> getAll() {
        return incidentDao.findAll();
    }

    public List<Incident> getAllPremise() {
        return incidentDao.findAllByPremiseIncidentIsNotNullAndSecurityIncidentIsNull();
    }

    public List<Incident> getAllSecurtiy() {
        return incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNull();
    }

    public List<Incident> getAllFire() {
        return incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNull();
    }

    public Incident findById(Integer id) {
        Optional<Incident> incident = incidentDao.findById(id);

        return incident.orElse(null);
    }

    public Incident save(Incident incident) {
        return incidentDao.save(incident);
    }

    @Transactional
    public boolean delete(Incident incident) {
        if (incident != null) {
            if (incident.getPremiseIncident() != null) {
                premiseIncidentService.delete(incident.getPremiseIncident());
            }
            if (incident.getSecurityIncident() != null) {
                securityIncidentService.delete(incident.getSecurityIncident());
            }
            incidentDao.delete(incident);
            return true;
        }
        return false;
    }

    public boolean delete(Integer id) {
        Incident incident = this.findById(id);

        if (incident != null) {
            return this.delete(incident);
        }
        return false;
    }

    public Incident getOne(int id) {
        return this.findById(id);
    }
}
