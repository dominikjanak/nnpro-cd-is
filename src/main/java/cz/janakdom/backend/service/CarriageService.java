package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.dto.CarriageDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "carriageService")
public class CarriageService {

    @Autowired
    private CarriageDao carriageDao;

    public Page<Carriage> findAll(Pageable pageable) {
        return carriageDao.findAllByIsDeletedFalse(pageable);
    }

    public Carriage findById(Integer id) {
        Optional<Carriage> carriage = carriageDao.findById(id);

        return carriage.orElse(null);
    }

    public Carriage findBySerialNumber(String serialNumber) {
        Optional<Carriage> carriage = carriageDao.findBySerialNumber(serialNumber);

        return carriage.orElse(null);
    }

    public Carriage save(CarriageDto inputModel) {
        Carriage carriage = this.findBySerialNumber(inputModel.getSerialNumber());

        if (carriage == null) {
            carriage = new Carriage();
            BeanUtils.copyProperties(inputModel, carriage, "id");
        } else {
            carriage.setIsDeleted(false);
        }

        return carriageDao.save(carriage);
    }

    public Carriage update(Integer id, CarriageDto inputModel) {
        Carriage carriage = this.findById(id);

        if (carriage != null) {
            BeanUtils.copyProperties(inputModel, carriage, "id", "serialNumber");
            carriageDao.save(carriage);
        }

        return carriage;
    }

    public boolean delete(Integer id) {
        Carriage carriage = this.findById(id);

        if (carriage != null) {
            // when delete dependency you can delete item
            /*if (damageType.getIsDeleted()) {
                return true;
            }*/
            if (carriage.getSecurityIncidents().size() > 0) {
                if(carriage.getIsDeleted()){
                    return false;
                }
                carriage.setIsDeleted(true);
                carriageDao.save(carriage);
            } else {
                carriageDao.delete(carriage);
            }
            return true;
        }

        return false;
    }
}
