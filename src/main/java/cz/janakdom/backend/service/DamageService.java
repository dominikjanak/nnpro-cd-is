package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.DamageDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.DamageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "damageService")
public class DamageService {

    @Autowired
    private DamageDao damageDao;
    @Autowired
    private DamageTypeService damageTypeService;

    public Damage findById(Integer id) {
        Optional<Damage> carriage = damageDao.findById(id);

        return carriage.orElse(null);
    }

    public boolean validateIncidentId(Damage damage, int incidentId) {
        boolean fireIdCheck = damage != null
                && damage.getFireIncident() != null
                && damage.getFireIncident().getSecurityIncident().getIncident().getId() == incidentId;
        boolean securityIdCheck = damage != null
                && damage.getSecurityIncident() != null
                && damage.getSecurityIncident().getIncident().getId() == incidentId;

        return fireIdCheck || securityIdCheck;
    }

    public boolean delete(Integer id) {
        Damage damage = findById(id);
        return this.delete(damage);
    }

    public boolean delete(Damage damage) {
        if (damage != null) {
            damageDao.delete(damage);
            return true;
        }
        return false;
    }

    public Damage addToIncident(Incident incident, DamageDto inputModel) throws Exception {
        Damage filled = fill(null, inputModel);

        if (incident == null || incident.getSecurityIncident() == null) {
            throw new Exception("VALID-INCIDENT-NOT-FOUND");
        }

        FireIncident fireIncident = incident.getSecurityIncident().getFireIncident();
        SecurityIncident securityIncident = incident.getSecurityIncident();

        if (fireIncident != null) {
            // ADD TO FIRE INCIDENT
            filled.setFireIncident(fireIncident);
        } else {
            // ADD TO SECURITY INCIDENT
            filled.setSecurityIncident(securityIncident);
        }
        damageDao.save(filled);
        return filled;
    }

    public Damage update(Damage damage, DamageDto inputModel) throws Exception {
        if (damage != null) {
            damage = fill(damage, inputModel);
            damageDao.save(damage);
        }
        return damage;
    }

    private Damage fill(Damage damage, DamageDto inputModel) throws Exception {
        if (damage == null) {
            damage = new Damage();
        }

        damage.setFinanceValue(inputModel.getFinanceValue());
        damage.setAttackedObject(inputModel.getAttackedObject());

        DamageType damageType = damageTypeService.findById(inputModel.getDamageTypeId());
        if (damageType == null) {
            throw new Exception("DAMAGE-TYPE-NOT-FOUND");
        }
        damage.setDamageType(damageType);
        return damage;
    }

    public String checkValidity(DamageDto inputModel) {
        if (inputModel.getFinanceValue() < 0) {
            return "INVALID-FINANCE-VALUE";
        }
        if (inputModel.getAttackedObject().isEmpty()) {
            return "INVALID-ATTACKED-OBJECT";
        }
        return "";
    }
}
