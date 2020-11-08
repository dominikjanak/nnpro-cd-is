package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.SecurityIncidentDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.incidents.SecurityIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "securityIncidentService")
public class SecurityIncidentService {

    @Autowired
    private SecurityIncidentDao securityIncidentDao;
    @Autowired
    private RegionService regionService;
    @Autowired
    private IncidentService incidentService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private CarriageService carriageService;
    @Autowired
    private RailroadService railroadService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttackedSubjectService attackedSubjectService;
    @Autowired
    private FireBrigadeUnitService fireBrigadeUnitService;
    @Autowired
    private FireIncidentService fireIncidentService;
    @Autowired
    private DamageService damageService;

    @Transactional
    public Incident save(SecurityIncidentDto inputModel) throws Exception {
        Incident incident = new Incident();
        SecurityIncident securityIncident = new SecurityIncident();

        this.fillData(incident, securityIncident, inputModel);
        incident.setSecurityIncident(securityIncident);
        securityIncident.setIncident(incident);

        securityIncidentDao.save(securityIncident);
        incidentService.save(incident);

        return incident;
    }

    public Incident save(SecurityIncident securityIncident){
        if(securityIncident != null) {
            securityIncidentDao.save(securityIncident);
            return securityIncident.getIncident();
        }
        return null;
    }

    public Incident update(Integer id, SecurityIncidentDto inputModel) throws Exception {
        Incident incident = incidentService.findById(id);

        if (incident.getSecurityIncident() == null) {
            return null;
        }

        SecurityIncident securityIncident = incident.getSecurityIncident();

        this.fillData(incident, securityIncident, inputModel);

        incidentService.save(incident);
        securityIncidentDao.save(securityIncident);

        return incident;
    }

    private void fillData(Incident incident, SecurityIncident securityIncident, SecurityIncidentDto inputModel) throws Exception {

        Region region = regionService.findById(inputModel.getRegion_id());
        if (region == null) {
            throw new Exception("REGION-NOT-FOUND");
        }
        incident.setRegion(region);

        if (incident.getOwner() == null) {
            incident.setOwner(securityContext.getAuthenticatedUser());
        }
        incident.setDescription(inputModel.getDescription());
        incident.setNote(inputModel.getNote());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());


        Carriage carriage = carriageService.findById(inputModel.getCarriage_id());
        if (carriage == null) {
            throw new Exception("CARRIAGE-NOT-FOUND");
        }
        securityIncident.setCarriage(carriage);

        Railroad railroad = railroadService.findById(inputModel.getRailroad_id());
        if (railroad == null) {
            throw new Exception("RAILROAD-NOT-FOUND");
        }
        securityIncident.setRailroad(railroad);

        User manager = userService.findById(inputModel.getManager_id());
        if (manager == null) {
            throw new Exception("MANAGER-NOT-FOUND");
        }
        securityIncident.setManager(manager);

        securityIncident.setPolice(inputModel.getPolice());
        securityIncident.setCrime(inputModel.getCrime());
        securityIncident.setChecked(inputModel.getChecked());

        // attacked subjects
        List<AttackedSubject> attackedSubjects = new ArrayList<>();
        for (Integer idx : inputModel.getAttackedSubject_ids()) {
            AttackedSubject as = attackedSubjectService.findById(idx);
            if (as == null) {
                throw new Exception("ATTACKED-SUBJECT-NOT-FOUND");
            }
            attackedSubjects.add(as);
        }
        securityIncident.setAttackedSubjects(attackedSubjects);

        // fire bridge units
        List<FireBrigadeUnit> fireBrigadeUnits = new ArrayList<>();
        for (Integer idx : inputModel.getFireBrigadeUnit_ids()) {
            FireBrigadeUnit fbu = fireBrigadeUnitService.findById(idx);
            if (fbu == null) {
                throw new Exception("FIRE-BRIGADE-UNIT-NOT-FOUND");
            }
            fireBrigadeUnits.add(fbu);
        }
        securityIncident.setFireBrigadeUnits(fireBrigadeUnits);
    }

    public boolean delete(SecurityIncident securityIncident) {
        if (securityIncident != null) {
            if(securityIncident.getFireIncident() != null){
                fireIncidentService.delete(securityIncident.getFireIncident());
            }
            for(Damage d : securityIncident.getDamages()) {
                damageService.delete(d);
            }
            securityIncidentDao.delete(securityIncident);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Incident incident = incidentService.findById(id);

        if (incident != null && incident.getSecurityIncident() != null) {
            incidentService.delete(incident);
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
