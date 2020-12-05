package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.HydrantDao;
import cz.janakdom.backend.dao.TechnicalSystemDao;
import cz.janakdom.backend.model.database.Hydrant;
import cz.janakdom.backend.model.database.TechnicalSystem;
import cz.janakdom.backend.model.dto.HydrantDto;
import cz.janakdom.backend.model.dto.TechnicalSystemDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "hydrantService")
public class HydrantService {

    @Autowired
    private HydrantDao hydrantDao;

    public List<Hydrant> findAll() {
        return hydrantDao.findAll();
    }

    public Hydrant findById(Integer id) {
        Optional<Hydrant> hydrant = hydrantDao.findById(id);
        return hydrant.orElse(null);
    }

    public Hydrant save(HydrantDto inputModel) {
        Hydrant hydrant = new Hydrant();
        BeanUtils.copyProperties(inputModel, hydrant, "id");

        return hydrantDao.save(hydrant);
    }

    public void deleteAllByBuildingId(Integer buildingId){
        hydrantDao.deleteAllByBuildingId(buildingId);
    }

    public boolean delete(Integer id) {
        Hydrant hydrant = this.findById(id);
        hydrantDao.delete(hydrant);
        return true;
    }
}
