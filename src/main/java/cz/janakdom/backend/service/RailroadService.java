package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.RailroadDao;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.model.external.RailroadExternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "railroadService")
public class RailroadService {

    @Autowired
    private RailroadDao railroadDao;
    @Autowired
    private ExternalDataService externalDataService;

    public Page<Railroad> findAll(Pageable pageable) {
        return railroadDao.findAllByIsDeletedFalse(pageable);
    }

    public Railroad findById(int id) {
        Optional<Railroad> interventionType = railroadDao.findById(id);

        return interventionType.orElse(null);
    }

    public Railroad findByNumberAndName(String number, String name) {
        Optional<Railroad> interventionType = railroadDao.findByNumberAndName(number, name);

        return interventionType.orElse(null);
    }

    private boolean save(String number, String name) {
        Railroad railroad = this.findByNumberAndName(number, name);

        if (railroad == null) {
            railroad = new Railroad();
            railroad.setNumber(number);
            railroad.setName(name);
        } else {
            railroad.setIsDeleted(false);
        }
        railroadDao.save(railroad);
        return true;
    }

    public boolean reload() {
        List<RailroadExternal> data = externalDataService.getRailroads();

        // Asi by bylo dobré provést to celé v transakci :D
        // ale to neumím
        if (data.size() > 0) {
            List<Railroad> all = railroadDao.findAll();
            boolean state = false;

            // šlo by to i lépe, než přes 2 cykly, ale peču na to!
            for (Railroad val : all) {
                state |= this.delete(val);
            }

            for (RailroadExternal val : data) {
                state |= this.save(val.getNumber(), val.getName());
            }
            return state;
        }
        return false;
    }

    private boolean delete(Railroad interventionType) {
        if (interventionType != null) {
            // when delete dependency you can delete item
            /*if (interventionType.getIsDeleted()) {
                return true;
            }*/
            if (interventionType.getSecurityIncidents().size() > 0) {
                if (interventionType.getIsDeleted()) {
                    return true;
                }
                interventionType.setIsDeleted(true);
                railroadDao.save(interventionType);
            } else {
                railroadDao.delete(interventionType);
            }
            return true;
        }

        return false;
    }
}
