package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.FireIncidentDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.incidents.FireIncidentDto;
import cz.janakdom.backend.model.dto.incidents.SecurityIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "fireIncidentService")
public class FireIncidentService {

    @Autowired
    private FireIncidentDao fireIncidentDao;
    @Autowired
    private SecurityIncidentService securityIncidentService;
    @Autowired
    private IncidentService incidentService;
    @Autowired
    private InterventionTypeService interventionTypeService;
    @Autowired
    private DamageService damageService;

    @Transactional
    public Incident save(FireIncidentDto inputModel) throws Exception {
        SecurityIncidentDto securityIncidentDto = fillSecurityIncidentDto(inputModel);

        Incident incident = securityIncidentService.save(securityIncidentDto);
        SecurityIncident securityIncident = incident.getSecurityIncident();

        FireIncident fireIncident = new FireIncident();
        fireIncident.setSecurityIncident(securityIncident);

        return fillAndPersist(inputModel, incident, fireIncident);
    }

    private Incident fillAndPersist(FireIncidentDto inputModel, Incident incident, FireIncident fireIncident) throws Exception {
        boolean validRange = fireIncident.setValidRange(inputModel.getValidFrom(), inputModel.getValidTo());
        if(!validRange) {
            throw new Exception("INVALID-DATE-RANGE");
        }

        InterventionType interventionType = interventionTypeService.findById(inputModel.getInterventionType_id());
        if (interventionType == null) {
            throw new Exception("INTERVENTION-TYPE-DOES-NOT-EXISTS");
        }
        fireIncident.setInterventionType(interventionType);

        SecurityIncident securityIncident = fireIncident.getSecurityIncident();
        securityIncident.setFireIncident(fireIncident);

        securityIncidentService.save(securityIncident);
        fireIncidentDao.save(fireIncident);

        return incident;
    }

    private SecurityIncidentDto fillSecurityIncidentDto(FireIncidentDto inputModel) {
        SecurityIncidentDto model = new SecurityIncidentDto();

        model.setChecked(inputModel.getChecked());
        model.setCrime(inputModel.getCrime());
        model.setPolice(inputModel.getPolice());
        model.setManager_id(inputModel.getManager_id());
        model.setCarriage_id(inputModel.getCarriage_id());
        model.setBuilding_id(inputModel.getBuilding_id());
        model.setRailroad_id(inputModel.getRailroad_id());
        model.setFireBrigadeUnit_ids(inputModel.getFireBrigadeUnit_ids());
        model.setAttackedSubject_ids(inputModel.getAttackedSubject_ids());
        model.setLocation(inputModel.getLocation());
        model.setCreationDatetime(inputModel.getCreationDatetime());
        model.setNote(inputModel.getNote());
        model.setDescription(inputModel.getDescription());
        model.setRegion_id(inputModel.getRegion_id());

        return model;
    }

    public Incident update(Integer id, FireIncidentDto inputModel) throws Exception {
        SecurityIncidentDto securityIncidentDto = fillSecurityIncidentDto(inputModel);

        Incident incident = securityIncidentService.update(id, securityIncidentDto);

        if(incident == null) {
            return null;
        }

        FireIncident fireIncident = incident.getSecurityIncident().getFireIncident();
        return fillAndPersist(inputModel, incident, fireIncident);
    }

    public boolean delete(FireIncident fireIncident) {
        if (fireIncident != null) {
            for(Damage d : fireIncident.getDamages()) {
                damageService.delete(d);
            }
            fireIncidentDao.delete(fireIncident);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Incident incident = incidentService.findById(id);

        if (incident != null && incident.getSecurityIncident() != null && incident.getSecurityIncident().getFireIncident() != null) {
            incidentService.delete(incident);
            return true;
        }
        return false;
    }

    public Incident getOne(int id) {
        Incident incident = incidentService.getOne(id);

        if (incident == null || incident.getSecurityIncident() == null || incident.getSecurityIncident().getFireIncident() == null) {
            return null;
        }
        return incident;
    }
}
