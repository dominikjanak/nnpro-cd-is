package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.DamageDao;
import cz.janakdom.backend.model.database.Damage;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.database.FireIncident;
import cz.janakdom.backend.model.database.SecurityIncident;
import cz.janakdom.backend.model.dto.DamageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "damageService")
public class DamageService {

    @Autowired
    private DamageDao damageDao;
    @Autowired
    private DamageTypeService damageTypeService;
    @Autowired
    private FireIncidentService fireIncidentService;
    @Autowired
    private SecurityIncidentService securityIncidentService;

    public List<Damage> findFireIncidentDamages(int fireIncidentId) {
        return damageDao.findAllByIsDeletedFalseAndFireIncidentIsNotNullAndFireIncident_Id(fireIncidentId);
    }

    public List<Damage> findSecurityIncidentDamages(int securityIncidentId) {
        return damageDao.findAllByIsDeletedFalseAndSecurityIncidentIsNotNullAndSecurityIncident_Id(securityIncidentId);
    }

    public Damage findById(Integer id) {
        Optional<Damage> carriage = damageDao.findById(id);

        return carriage.orElse(null);
    }

    public Damage save(DamageDto inputModel) throws Exception {
        Damage damage = fill(null, inputModel);
        return damageDao.save(damage);
    }

    public Damage update(Integer id, DamageDto inputModel) {
        Damage damage = findById(id);

        if (damage != null) {
            damage = fill(damage, inputModel);
            damageDao.save(damage);
        }

        return damage;
    }

    public boolean delete(Integer id) {
        Damage damage = findById(id);
        if (damage != null) {
            damageDao.delete(damage);
            return true;
        }
        return false;
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

        if (inputModel.getFireIncidentId() != null) {
            FireIncident fireIncident = fireIncidentService.findById(inputModel.getFireIncidentId());
            if (fireIncident == null) {
                throw new Exception("FIRE-INCIDENT-NOT-FOUND");
            }
            damage.setFireIncident(fireIncident);
        }

        if (inputModel.getSecurityIncidentId() != null) {
            SecurityIncident securityIncident = securityIncidentService.findById(inputModel.getSecurityIncidentId());
            if (securityIncident == null) {
                throw new Exception("SECURITY-INCIDENT-NOT-FOUND");
            }
            damage.setSecurityIncident(securityIncident);
        }
        return damage;
    }
}
