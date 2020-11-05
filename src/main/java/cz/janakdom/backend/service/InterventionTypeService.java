package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.InterventionTypeDao;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.model.external.TypeOfInterventionExternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "interventionTypeService")
public class InterventionTypeService {

    @Autowired
    private InterventionTypeDao interventionTypeDao;
    @Autowired
    private ExternalDataService externalDataService;

    public List<InterventionType> findAll() {
        return interventionTypeDao.findAllByIsDeletedFalse();
    }

    public InterventionType findById(int id) {
        Optional<InterventionType> interventionType = interventionTypeDao.findById(id);

        return interventionType.orElse(null);
    }

    public InterventionType findByName(String name) {
        Optional<InterventionType> interventionType = interventionTypeDao.findByName(name);

        return interventionType.orElse(null);
    }

    private boolean save(String name) {
        InterventionType interventionType = this.findByName(name);

        if (interventionType == null) {
            interventionType = new InterventionType();
            interventionType.setName(name);
        } else {
            interventionType.setIsDeleted(false);
        }
        interventionTypeDao.save(interventionType);
        return true;
    }

    @Transactional
    public boolean reload() {
        List<TypeOfInterventionExternal> data = externalDataService.getTypeOfIntervention();

        // Asi by bylo dobré provést to celé v transakci :D
        // ale to neumím
        if (data.size() > 0) {
            List<InterventionType> all = interventionTypeDao.findAll();
            boolean state = false;

            // šlo by to i lépe, než přes 2 cykly, ale peču na to!
            for (InterventionType val : all) {
                state |= this.delete(val);
            }

            for (TypeOfInterventionExternal val : data) {
                state |= this.save(val.getName());
            }
            return state;
        }
        return false;
    }

    private boolean delete(InterventionType interventionType) {
        if (interventionType != null) {
            // when delete dependency you can delete item
            /*if (interventionType.getIsDeleted()) {
                return true;
            }*/
            if (interventionType.getFireIncidents().size() > 0) {
                if (interventionType.getIsDeleted()) {
                    return true;
                }
                interventionType.setIsDeleted(true);
                interventionTypeDao.save(interventionType);
            } else {
                interventionTypeDao.delete(interventionType);
            }
            return true;
        }

        return false;
    }
}
