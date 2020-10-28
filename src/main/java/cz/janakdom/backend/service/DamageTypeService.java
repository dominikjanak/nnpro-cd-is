package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.DamageTypeDao;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "damageTypeService")
public class DamageTypeService {

    @Autowired
    private DamageTypeDao damageTypeDao;

    public Page<DamageType> findAll(Pageable pageable){
        return damageTypeDao.findAll(pageable);
    }

    public DamageType findById(int id){
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        return damageType.orElse(null);
    }

    public DamageType findByName(String name){
        Optional<DamageType> damageType = damageTypeDao.findByName(name);

        return damageType.orElse(null);
    }

    public DamageType save(DamageTypeDto inputModel){
        DamageType damageType = new DamageType();
        BeanUtils.copyProperties(inputModel, damageType, "id");

        return damageTypeDao.save(damageType);
    }

    public DamageType update(Integer id, DamageTypeDto inputModel){
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        if (damageType.isPresent()){
            BeanUtils.copyProperties(inputModel, damageType.get(), "id");
            damageTypeDao.save(damageType.get());
        }

        return damageType.orElse(null);
    }

    public boolean delete(int id){
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        //TODO: může být odstrněno, pouze pokud není navázáno na záznam v DB
        //damageType.getDamages(); // musí být prázdné pole v opačném pípadě je třeba nastavit pouze příznak isDeleted
        // isDelete není součástí struktury (nutné přidat) jak do entity, tak do resources/db.migration


        /*if(damageType.isPresent()){
            damageTypeDao.delete(damageType.get());

            return true;
        }*/

        return false;
    }
}
