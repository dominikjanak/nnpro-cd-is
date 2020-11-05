package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.PremiseIncidentDao;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.PremiseIncident;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.dto.PremiseIncidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "premiseIncidentService")
public class PremiseIncidentService {

    @Autowired
    private PremiseIncidentDao premiseIncidentDao;

    @Autowired
    private RegionService regionService;

    public Page<PremiseIncident> findAll(Pageable pageable) {
        return premiseIncidentDao.findAllByIsDeletedFalse(pageable);
    }

    public PremiseIncident findById(Integer id) {
        Optional<PremiseIncident> carriage = premiseIncidentDao.findById(id);

        return carriage.orElse(null);
    }

    @Transactional
    public PremiseIncident save(PremiseIncidentDto inputModel) throws Exception {
        Incident incident = new Incident();
        incident.setComment(inputModel.getComment());
        incident.setCreationDatetime(inputModel.getCreationDatetime());
        incident.setLocation(inputModel.getLocation());
        incident.setNote(inputModel.getNote());

        Region region = regionService.findById(inputModel.getRegion_id());

        if(region == null){
            throw new Exception("Region does not exist!");
        }

        incident.setRegion(region);

        PremiseIncident premiseIncident = new PremiseIncident();
        premiseIncident.setValid(inputModel.getValid());
        incident.setPremiseIncident(premiseIncident);

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
