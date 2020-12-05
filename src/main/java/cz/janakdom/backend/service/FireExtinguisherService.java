package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.FireExtinguisherDao;
import cz.janakdom.backend.model.database.Building;
import cz.janakdom.backend.model.database.FireExtinguisher;
import cz.janakdom.backend.model.database.Hydrant;
import cz.janakdom.backend.model.dto.FireExtinguisherDto;
import cz.janakdom.backend.model.dto.TechnicalSystemDto;
import cz.janakdom.backend.model.enums.ExtinguisherType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "fireExtinguisherService")
public class FireExtinguisherService {

    @Autowired
    private FireExtinguisherDao fireExtinguisherDao;
    @Autowired
    private BuildingService buildingService;

    public List<FireExtinguisher> findAll() {
        return fireExtinguisherDao.findAll();
    }

    public FireExtinguisher findById(Integer id) {
        Optional<FireExtinguisher> fireExtinguisher = fireExtinguisherDao.findById(id);
        return fireExtinguisher.orElse(null);
    }

    public FireExtinguisher save(FireExtinguisherDto inputModel) {
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        BeanUtils.copyProperties(inputModel, fireExtinguisher, "id");

        for (ExtinguisherType type : ExtinguisherType.values()) {
            System.out.println(type.toString());
            if (type.toString().compareTo(inputModel.getType()) == 0){
                fireExtinguisher.setType(type);
                break;
            }
        }


        Building building = buildingService.findById(inputModel.getBuilding_id());
        if (building != null && fireExtinguisher.getType() != null){
            fireExtinguisher.setBuilding(building);
            return fireExtinguisherDao.save(fireExtinguisher);
        } else{
            return null;
        }
    }

    public void deleteAllByBuildingId(Integer buildingId){
        fireExtinguisherDao.deleteAllByBuildingId(buildingId);
    }

    public boolean delete(Integer id) {
        FireExtinguisher fireExtinguisher = this.findById(id);
        fireExtinguisherDao.delete(fireExtinguisher);
        return true;
    }
}
