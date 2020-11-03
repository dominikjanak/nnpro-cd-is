package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.PremiseIncidentDao;
import cz.janakdom.backend.model.database.PremiseIncident;
import cz.janakdom.backend.model.dto.PremiseIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "premiseIncidentService")
public class PremiseIncidentService {

    @Autowired
    private PremiseIncidentDao premiseIncidentDao;

    public Page<PremiseIncident> findAll(Pageable pageable) {
        return premiseIncidentDao.findAllByIsDeletedFalse(pageable);
    }

    public PremiseIncident findById(Integer id) {
        Optional<PremiseIncident> carriage = premiseIncidentDao.findById(id);

        return carriage.orElse(null);
    }

    public PremiseIncident save(PremiseIncidentDto inputModel) {
        PremiseIncident premiseIncident = new PremiseIncident();
        premiseIncident.setValid(inputModel.getValid());

        return premiseIncidentDao.save(premiseIncident);
    }

    public PremiseIncident update(Integer id, PremiseIncidentDto inputModel) {
        PremiseIncident premiseIncident = this.findById(id);

        if (premiseIncident != null) {
            premiseIncident.setValid(inputModel.getValid());
            premiseIncidentDao.save(premiseIncident);
        }

        return premiseIncident;
    }

    public boolean delete(Integer id) {
        PremiseIncident premiseIncident = this.findById(id);

        if (premiseIncident != null) {
            // when delete dependency you can delete item
            /*if (damageType.getIsDeleted()) {
                return true;
            }*/
            if (premiseIncident.getIncident() != null) {
                if (premiseIncident.getIsDeleted()) {
                    return false;
                }
                premiseIncident.setIsDeleted(true);
                premiseIncidentDao.save(premiseIncident);
            } else {
                premiseIncidentDao.delete(premiseIncident);
            }
            return true;
        }
        return false;
    }
}
