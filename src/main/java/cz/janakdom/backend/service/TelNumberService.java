package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.BuildingDao;
import cz.janakdom.backend.dao.TelNumberDao;
import cz.janakdom.backend.model.database.Building;
import cz.janakdom.backend.model.database.TelNumber;
import cz.janakdom.backend.model.dto.TelNumberDto;
import cz.janakdom.backend.model.dto.building.BuildingDto;
import cz.janakdom.backend.model.dto.building.BuildingUpdateDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "telNUmberService")
public class TelNumberService {

    @Autowired
    private TelNumberDao telNumberDao;

    public List<TelNumber> findAll() {
        return telNumberDao.findAll();
    }

    public TelNumber findById(Integer id) {
        Optional<TelNumber> telNumber = telNumberDao.findById(id);

        return telNumber.orElse(null);
    }

    public TelNumber save(TelNumberDto inputModel) {
        TelNumber telNumber = new TelNumber();
        BeanUtils.copyProperties(inputModel, telNumber, "id");

        return telNumberDao.save(telNumber);
    }

    public void deleteAllByBuildingId(Integer buildingId){
        telNumberDao.deleteAllByBuildingId(buildingId);
    }

    public boolean delete(Integer id) {
        TelNumber telNumber = this.findById(id);
        telNumberDao.delete(telNumber);
        return true;
    }
}
