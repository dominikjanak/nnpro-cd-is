package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.SecurityIncidentDao;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.database.SecurityIncident;
import cz.janakdom.backend.model.dto.incidents.PremiseIncidentDto;
import cz.janakdom.backend.model.dto.incidents.SecurityIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "securityIncidentService")
public class SecurityIncidentService {

    @Autowired
    private SecurityIncidentDao securityIncidentDao;
    @Autowired
    private RegionService regionService;
    @Autowired
    private IncidentService incidentService;

    private SecurityIncident findById(Integer id) {
        Optional<SecurityIncident> carriage = securityIncidentDao.findById(id);

        return carriage.orElse(null);
    }

    @Transactional
    public Incident save(PremiseIncidentDto inputModel) throws Exception {
        Incident incident = new Incident();
        incident.setDescription(inputModel.getDescription());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());
        incident.setNote(inputModel.getNote());

        Region region = regionService.findById(inputModel.getRegion_id());

        if (region == null) {
            throw new Exception("Region does not exist!");
        }

        incident.setRegion(region);

        SecurityIncident securityIncident = new SecurityIncident();
        // TODO: FILL SECURITY INCIDENT

        incident.setSecurityIncident(securityIncident);

        securityIncidentDao.save(securityIncident);
        incidentService.save(incident);

        return incident;
    }

    public Incident update(Integer id, SecurityIncidentDto inputModel) {
        Incident incident = incidentService.findById(id);

        if (incident.getSecurityIncident() == null) {
            return null;
        }

        incident.setDescription(inputModel.getDescription());
        incident.setRegion(regionService.findById(inputModel.getRegion_id()));
        incident.setNote(inputModel.getNote());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());

        SecurityIncident securityIncident = incident.getSecurityIncident();
        // TODO: FILL SECURITY INCIDENT

        securityIncidentDao.save(securityIncident);
        incidentService.save(incident);

        return incident;
    }

    public boolean delete(SecurityIncident securityIncident) {
        if (securityIncident != null) {
            securityIncidentDao.delete(securityIncident);
            return true;
        }
        return false;
    }

    public Incident getOne(int id) {
        Incident securityIncident = incidentService.getOne(id);

        if (securityIncident == null || securityIncident.getSecurityIncident() == null) {
            return null;
        }
        return securityIncident;
    }
}
