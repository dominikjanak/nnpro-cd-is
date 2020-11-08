package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.PremiseIncidentDao;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.PremiseIncident;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.dto.incidents.PremiseIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "premiseIncidentService")
public class PremiseIncidentService {

    @Autowired
    private PremiseIncidentDao premiseIncidentDao;

    @Autowired
    private RegionService regionService;
    @Autowired
    private IncidentService incidentService;
    @Autowired
    private SecurityContext securityContext;

    private PremiseIncident findById(Integer id) {
        Optional<PremiseIncident> carriage = premiseIncidentDao.findById(id);

        return carriage.orElse(null);
    }

    @Transactional
    public Incident save(PremiseIncidentDto inputModel) throws Exception {
        Incident incident = new Incident();
        incident.setDescription(inputModel.getDescription());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());
        incident.setNote(inputModel.getNote());
        incident.setOwner(securityContext.getAuthenticatedUser());

        Region region = regionService.findById(inputModel.getRegion_id());

        if (region == null) {
            throw new Exception("Region does not exist!");
        }

        incident.setRegion(region);

        PremiseIncident premiseIncident = new PremiseIncident();
        premiseIncident.setValid(inputModel.getValid());

        premiseIncident.setIncident(incident);
        incident.setPremiseIncident(premiseIncident);

        incidentService.save(incident);
        premiseIncidentDao.save(premiseIncident);

        return incident;
    }

    public Incident update(Integer id, PremiseIncidentDto inputModel) throws Exception {
        Incident incident = incidentService.findById(id);

        if (incident.getPremiseIncident() == null) {
            return null;
        }

        incident.setDescription(inputModel.getDescription());

        Region region = regionService.findById(inputModel.getRegion_id());
        if (region == null) {
            throw new Exception("Region does not exist!");
        }
        incident.setRegion(region);

        incident.setNote(inputModel.getNote());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());

        incident.getPremiseIncident().setValid(inputModel.getValid());
        premiseIncidentDao.save(incident.getPremiseIncident());
        incidentService.save(incident);

        return incident;
    }

    public boolean delete(PremiseIncident premiseIncident) {
        if (premiseIncident != null) {
            premiseIncidentDao.delete(premiseIncident);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Incident incident = incidentService.findById(id);

        if (incident != null && incident.getPremiseIncident() != null) {
            incidentService.delete(incident);
            return true;
        }
        return false;
    }

    public Incident getOne(int id) {
        Incident incidentResult = incidentService.getOne(id);

        if (incidentResult == null || incidentResult.getPremiseIncident() == null) {
            return null;
        }
        return incidentResult;
    }
}
