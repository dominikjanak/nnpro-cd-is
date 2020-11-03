package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.AreaDao;
import cz.janakdom.backend.model.database.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "areaService")
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    public List<Area> findAll() {
        return areaDao.findAllByIsDeletedFalse();
    }

    public Area findById(int id) {
        Optional<Area> interventionType = areaDao.findById(id);

        return interventionType.orElse(null);
    }
}
