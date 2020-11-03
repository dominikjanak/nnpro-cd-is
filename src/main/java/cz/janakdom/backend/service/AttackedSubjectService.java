package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.AttackedSubjectDao;
import cz.janakdom.backend.model.database.AttackedSubject;
import cz.janakdom.backend.model.dto.AttackedSubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "attackedSubjectService")
public class AttackedSubjectService {

    @Autowired
    private AttackedSubjectDao attackedSubjectDao;

    public Page<AttackedSubject> findAll(Pageable pageable) {
        return attackedSubjectDao.findAllByIsDeletedFalse(pageable);
    }

    public AttackedSubject findById(Integer id) {
        Optional<AttackedSubject> carriage = attackedSubjectDao.findById(id);

        return carriage.orElse(null);
    }

    public AttackedSubject findByName(String name) {
        Optional<AttackedSubject> carriage = attackedSubjectDao.findByName(name);

        return carriage.orElse(null);
    }

    public AttackedSubject save(AttackedSubjectDto inputModel) {
        AttackedSubject attackedSubject = this.findByName(inputModel.getName());

        if (attackedSubject == null) {
            attackedSubject = new AttackedSubject();
            attackedSubject.setName(inputModel.getName());
        } else {
            attackedSubject.setIsDeleted(false);
        }

        return attackedSubjectDao.save(attackedSubject);
    }

    public AttackedSubject update(Integer id, AttackedSubjectDto inputModel) {
        AttackedSubject attackedSubject = this.findById(id);

        if (attackedSubject != null) {
            attackedSubject.setName(inputModel.getName());
            attackedSubjectDao.save(attackedSubject);
        }

        return attackedSubject;
    }

    public boolean delete(Integer id) {
        AttackedSubject attackedSubject = this.findById(id);

        if (attackedSubject != null) {
            // when delete dependency you can delete item
            /*if (damageType.getIsDeleted()) {
                return true;
            }*/
            if (attackedSubject.getSecurityIncidents().size() > 0) {
                if(attackedSubject.getIsDeleted()){
                    return false;
                }
                attackedSubject.setIsDeleted(true);
                attackedSubjectDao.save(attackedSubject);
            } else {
                attackedSubjectDao.delete(attackedSubject);
            }
            return true;
        }

        return false;
    }
}
