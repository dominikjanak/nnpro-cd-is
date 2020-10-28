package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.InterventionTypeDao;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.model.dto.InterventionTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "interventionTypeService")
public class InterventionTypeService {

    @Autowired
    private InterventionTypeDao interventionTypeDao;

    public List<InterventionType> findAll(){
        List<InterventionType> interventionTypes = new ArrayList<>();
        interventionTypeDao.findAll().iterator().forEachRemaining(interventionTypes::add);

        return interventionTypes;
    }

    public InterventionType findById(int id){
        Optional<InterventionType> interventionType = interventionTypeDao.findById(id);

        return interventionType.orElse(null);
    }

    public InterventionType save(InterventionTypeDto inputModel){
        InterventionType interventionType = new InterventionType();
        BeanUtils.copyProperties(inputModel, interventionType, "id");

        return interventionTypeDao.save(interventionType);
    }

    public InterventionType update(InterventionTypeDto inputModel){
        Optional<InterventionType> interventionType = interventionTypeDao.findById(inputModel.getId());

        if(interventionType.isPresent()){
            BeanUtils.copyProperties(inputModel, interventionType, "id");
            interventionTypeDao.save(interventionType.get());
        }

        return interventionType.orElse(null);
    }

    public boolean delete(int id){
        Optional<InterventionType> interventionType = interventionTypeDao.findById(id);

        if(interventionType.isPresent()){
            interventionTypeDao.delete(interventionType.get());

            return true;
        }

        return false;
    }
}
