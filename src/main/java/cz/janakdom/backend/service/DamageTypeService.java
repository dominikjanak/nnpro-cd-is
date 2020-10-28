package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.DamageTypeDao;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "damageTypeService")
public class DamageTypeService {

    @Autowired
    private DamageTypeDao damageTypeDao;

    public List<DamageType> findAll(){
        List<DamageType> damageTypes = new ArrayList<>();
        damageTypeDao.findAll().iterator().forEachRemaining(damageTypes::add);

        return damageTypes;
    }

    public DamageType findById(int id){
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        return damageType.orElse(null);
    }

    public DamageType save(DamageTypeDto inputModel){
        DamageType damageType = new DamageType();
        BeanUtils.copyProperties(inputModel, damageType, "id");

        return damageTypeDao.save(damageType);
    }

    public DamageType update(DamageTypeDto inputModel){
        Optional<DamageType> damageType = damageTypeDao.findById(inputModel.getId());

        if (damageType.isPresent()){
            BeanUtils.copyProperties(inputModel, damageType.get(), "id");
            damageTypeDao.save(damageType.get());
        }

        return damageType.orElse(null);
    }

    public boolean delete(int id){
        Optional<DamageType> damageType = damageTypeDao.findById(id);

        if(damageType.isPresent()){
            damageTypeDao.delete(damageType.get());

            return true;
        }

        return false;
    }
}
