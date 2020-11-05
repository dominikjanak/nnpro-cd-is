package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.DamageTypeDao;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "damageTypeService")
public class DamageTypeService {

    @Autowired
    private DamageTypeDao damageTypeDao;

    public List<DamageType> findAll() {
        return damageTypeDao.findAllByIsDeletedFalse();
    }

    public DamageType findById(int id) {
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        return damageType.orElse(null);
    }

    public DamageType findByName(String name) {
        Optional<DamageType> damageType = damageTypeDao.findByName(name);

        return damageType.orElse(null);
    }

    public DamageType save(DamageTypeDto inputModel) {
        DamageType damageType = this.findByName(inputModel.getName());

        if (damageType == null) {
            damageType = new DamageType();
            BeanUtils.copyProperties(inputModel, damageType, "id");
        } else {
            damageType.setIsDeleted(false);
        }

        return damageTypeDao.save(damageType);
    }

    public DamageType update(Integer id, DamageTypeDto inputModel) {
        DamageType damageType = this.findById(id);

        if (damageType != null) {
            BeanUtils.copyProperties(inputModel, damageType, "id");
            damageTypeDao.save(damageType);
        }

        return damageType;
    }

    public boolean delete(int id) {
        DamageType damageType = this.findById(id);

        if (damageType != null) {
            // when delete dependency you can delete item
            /*if (damageType.getIsDeleted()) {
                return true;
            }*/
            if (damageType.getDamages().size() > 0) {
                if(damageType.getIsDeleted()){
                    return false;
                }
                damageType.setIsDeleted(true);
                damageTypeDao.save(damageType);
            } else {
                damageTypeDao.delete(damageType);
            }
            return true;
        }

        return false;
    }
}
