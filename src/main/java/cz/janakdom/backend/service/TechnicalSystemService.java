package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.TechnicalSystemDao;
import cz.janakdom.backend.dao.TelNumberDao;
import cz.janakdom.backend.model.database.Building;
import cz.janakdom.backend.model.database.TechnicalSystem;
import cz.janakdom.backend.model.database.TelNumber;
import cz.janakdom.backend.model.dto.TechnicalSystemDto;
import cz.janakdom.backend.model.dto.TelNumberDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "technicalSystemService")
public class TechnicalSystemService {

    @Autowired
    private TechnicalSystemDao technicalSystemDao;
    @Autowired
    private BuildingService buildingService;

    public List<TechnicalSystem> findAll() {
        return technicalSystemDao.findAll();
    }

    public TechnicalSystem findById(Integer id) {
        Optional<TechnicalSystem> telNumber = technicalSystemDao.findById(id);

        return telNumber.orElse(null);
    }

    public TechnicalSystem save(TechnicalSystemDto inputModel) {
        TechnicalSystem technicalSystem = new TechnicalSystem();
        BeanUtils.copyProperties(inputModel, technicalSystem, "id");
        Building building = buildingService.findById(inputModel.getBuilding_id());
        if (building != null){
            technicalSystem.setBuilding(building);
            return technicalSystemDao.save(technicalSystem);
        } else{
            return null;
        }
    }

    public void deleteAllByBuildingId(Integer buildingId){
        technicalSystemDao.deleteAllByBuildingId(buildingId);
    }

    public boolean delete(Integer id) {
        TechnicalSystem technicalSystem = this.findById(id);
        technicalSystemDao.delete(technicalSystem);
        return true;
    }
}
