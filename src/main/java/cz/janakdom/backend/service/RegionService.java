package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.RegionDao;
import cz.janakdom.backend.model.database.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "regionService")
public class RegionService {

    @Autowired
    private RegionDao regionDao;

    public List<Region> findAll() {
        return regionDao.findAllByIsDeletedFalse();
    }

    public Region findById(int id) {
        Optional<Region> interventionType = regionDao.findById(id);

        return interventionType.orElse(null);
    }

    public Region findByAbbreviation(String abbreviation) {
        Optional<Region> interventionType = regionDao.findByAbbreviation(abbreviation);

        return interventionType.orElse(null);
    }
}
