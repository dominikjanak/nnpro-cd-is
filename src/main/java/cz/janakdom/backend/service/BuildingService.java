package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.BuildingDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.building.BuildingDto;
import cz.janakdom.backend.model.dto.building.BuildingUpdateDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "buildingService")
public class BuildingService {

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private EPSService epsService;
    @Autowired
    private FireExtinguisherService fireExtinguisherService;
    @Autowired
    private HydrantService hydrantService;
    @Autowired
    private TechnicalSystemService technicalSystemService;
    @Autowired
    private TelNumberService telNumberService;

    @Autowired
    private FileService fileService;

    public List<Building> findAll() {
        return buildingDao.findAll();
    }

    public Building findById(Integer id) {
        Optional<Building> building = buildingDao.findById(id);

        return building.orElse(null);
    }

    public List<Building> findAllNotDeleted(){
        return buildingDao.findAllByIsDeletedFalse();
    }

    public Building findByInnerno(String innerno) {
        Optional<Building> building = buildingDao.findByInnerno(innerno);

        return building.orElse(null);
    }

    public Building save(BuildingDto inputModel) {
        Building building = this.findByInnerno(inputModel.getInnerno());

        if (building == null) {
            building = new Building();
            BeanUtils.copyProperties(inputModel, building, "id");
        } else {
            building.setIsDeleted(false);
        }

        return buildingDao.save(building);
    }

    public Building update(Integer id, BuildingUpdateDto inputModel) {
        Building building = this.findById(id);

        if (building != null) {
            BeanUtils.copyProperties(inputModel, building, "id", "innerno", "gps", "address");
            buildingDao.save(building);
        }

        return building;
    }

    @Transactional
    public boolean delete(Integer id) {
        Building building = this.findById(id);

        if (building != null) {
            if (building.getSecurityIncidents().size() > 0) {
                if(building.getIsDeleted()){
                    return false;
                }
                building.setIsDeleted(true);
                buildingDao.save(building);
            } else {
                epsService.deleteAllByBuildingId(building.getId());
                fireExtinguisherService.deleteAllByBuildingId(building.getId());
                hydrantService.deleteAllByBuildingId(building.getId());
                technicalSystemService.deleteAllByBuildingId(building.getId());
                telNumberService.deleteAllByBuildingId(building.getId());
                fileService.deleteAllByBuildingId(building.getId());
                buildingDao.delete(building);
            }
            return true;
        }

        return false;
    }
}
