package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.dao.FireBrigadeUnitDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.database.FireBrigadeUnit;
import cz.janakdom.backend.model.dto.FireBrigadeUnitDto;
import cz.janakdom.backend.model.dto.carriage.CarriageDto;
import cz.janakdom.backend.model.dto.carriage.CarriageUpdateDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "fireBrigadeUnitService")
public class FireBrigadeUnitService {

    @Autowired
    private FireBrigadeUnitDao fireBrigadeUnitDao;

    public Page<FireBrigadeUnit> findAll(Pageable pageable) {
        return fireBrigadeUnitDao.findAllByIsDeletedFalse(pageable);
    }

    public FireBrigadeUnit findById(Integer id) {
        Optional<FireBrigadeUnit> carriage = fireBrigadeUnitDao.findById(id);

        return carriage.orElse(null);
    }

    public FireBrigadeUnit findByName(String name) {
        Optional<FireBrigadeUnit> carriage = fireBrigadeUnitDao.findByName(name);

        return carriage.orElse(null);
    }

    public FireBrigadeUnit save(FireBrigadeUnitDto inputModel) {
        FireBrigadeUnit fireBrigadeUnit = this.findByName(inputModel.getName());

        if (fireBrigadeUnit == null) {
            fireBrigadeUnit = new FireBrigadeUnit();
            fireBrigadeUnit.setName(inputModel.getName());
        } else {
            fireBrigadeUnit.setIsDeleted(false);
        }

        return fireBrigadeUnitDao.save(fireBrigadeUnit);
    }

    public FireBrigadeUnit update(Integer id, FireBrigadeUnitDto inputModel) {
        FireBrigadeUnit fireBrigadeUnit = this.findById(id);

        if (fireBrigadeUnit != null) {
            fireBrigadeUnit.setName(inputModel.getName());
            fireBrigadeUnitDao.save(fireBrigadeUnit);
        }

        return fireBrigadeUnit;
    }

    public boolean delete(Integer id) {
        FireBrigadeUnit fireBrigadeUnit = this.findById(id);

        if (fireBrigadeUnit != null) {
            // when delete dependency you can delete item
            /*if (damageType.getIsDeleted()) {
                return true;
            }*/
            if (fireBrigadeUnit.getSecurityIncidents().size() > 0) {
                if(fireBrigadeUnit.getIsDeleted()){
                    return false;
                }
                fireBrigadeUnit.setIsDeleted(true);
                fireBrigadeUnitDao.save(fireBrigadeUnit);
            } else {
                fireBrigadeUnitDao.delete(fireBrigadeUnit);
            }
            return true;
        }

        return false;
    }
}
