package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.InterventionTypeDao;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.model.dto.InterventionTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "interventionTypeService")
public class InterventionTypeService {

    @Autowired
    private InterventionTypeDao interventionTypeDao;

    public Page<InterventionType> findAll(Pageable pageable){
        return interventionTypeDao.findAllByIsDeletedFalse(pageable);
    }

    public InterventionType findById(int id){
        Optional<InterventionType> interventionType = interventionTypeDao.findById(id);

        return interventionType.orElse(null);
    }

    private InterventionType save(String name){
        /*InterventionType interventionType = new InterventionType();
        interventionType.setName(name);
        BeanUtils.copyProperties(inputModel, interventionType, "id");

        return interventionTypeDao.save(interventionType);*/
        return null;
    }

    public boolean reload(){
        /*
        V případě externích číselníků bude použit příznak isDeleted (true = nebude se zobrazovat pro nová zadání)
        Postup přehrání dat:
            1) snažím se odstranit jeden prvek po druhém (pro všechny):
                - pokud není na nic navázán, odstraním ho
                - pokud je navázán, označím ho za isDeleted = true
            2) načtu data z externího systému
            3) uložím data do databáze
                - kouknu pokud prvek existuje (podle všech hodnot (id neuvažuji)) přepíši pouze isDeleted = false
                - když prvek neexistuje přidám nový prvek (id neřeším)
         */

        /*Optional<InterventionType> interventionType = interventionTypeDao.findById(inputModel.getId());

        if(interventionType.isPresent()){
            BeanUtils.copyProperties(inputModel, interventionType, "id");
            interventionTypeDao.save(interventionType.get());
        }*/

        return false;
    }

    private boolean delete(int id){
        Optional<InterventionType> interventionType = interventionTypeDao.findById(id);

        //TODO: Stejný postup jako jako v DamageType

        /*if(interventionType.isPresent()){
            interventionTypeDao.delete(interventionType.get());

            return true;
        }*/

        return false;
    }
}
