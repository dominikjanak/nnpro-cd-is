package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.AttackedSubjectDao;
import cz.janakdom.backend.model.database.AttackedSubject;
import cz.janakdom.backend.model.external.AttackedEntityExternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "attackedSubjectService")
public class AttackedSubjectService {

    @Autowired
    private AttackedSubjectDao attackedSubjectDao;
    @Autowired
    private ExternalDataService externalDataService;

    public List<AttackedSubject> findAll() {
        return attackedSubjectDao.findAllByIsDeletedFalse();
    }

    public AttackedSubject findById(int id) {
        Optional<AttackedSubject> interventionType = attackedSubjectDao.findById(id);

        return interventionType.orElse(null);
    }

    public AttackedSubject findByName(String name) {
        Optional<AttackedSubject> interventionType = attackedSubjectDao.findByName(name);

        return interventionType.orElse(null);
    }

    private boolean save(String name) {
        AttackedSubject attackedSubject = this.findByName(name);

        if (attackedSubject == null) {
            attackedSubject = new AttackedSubject();
            attackedSubject.setName(name);
        } else {
            attackedSubject.setIsDeleted(false);
        }
        attackedSubjectDao.save(attackedSubject);
        return true;
    }

    @Transactional
    public boolean reload() {
        List<AttackedEntityExternal> data = externalDataService.getAffectedEntities();

        // Asi by bylo dobré provést to celé v transakci :D
        // ale to neumím
        if (data.size() > 0) {
            List<AttackedSubject> all = attackedSubjectDao.findAll();
            boolean state = false;

            // šlo by to i lépe, než přes 2 cykly, ale peču na to!
            for (AttackedSubject val : all) {
                state |= this.delete(val);
            }

            for (AttackedEntityExternal val : data) {
                state |= this.save(val.getName());
            }
            return state;
        }
        return false;
    }

    private boolean delete(AttackedSubject interventionType) {
        if (interventionType != null) {
            // when delete dependency you can delete item
            /*if (interventionType.getIsDeleted()) {
                return true;
            }*/
            if (interventionType.getSecurityIncidents().size() > 0) {
                if (interventionType.getIsDeleted()) {
                    return true;
                }
                interventionType.setIsDeleted(true);
                attackedSubjectDao.save(interventionType);
            } else {
                attackedSubjectDao.delete(interventionType);
            }
            return true;
        }

        return false;
    }
}
