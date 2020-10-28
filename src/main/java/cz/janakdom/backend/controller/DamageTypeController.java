package cz.janakdom.backend.controller;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import cz.janakdom.backend.service.DamageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/damageType")
public class DamageTypeController {

    @Autowired
    private DamageTypeService damageTypeService;

    @GetMapping("/findAll")
    public ApiResponse<List<DamageType>> findDamageTypes(){
        return new ApiResponse<>(200, "SUCCESS", damageTypeService.findAll());
    }

    @GetMapping("/find/{id}")
    public ApiResponse<DamageType> findDamageType(@PathVariable int id){
        DamageType damageType = damageTypeService.findById(id);

        if (damageType != null){
            return  new ApiResponse<>(200, "SUCCESS", damageType);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PostMapping("/create")
    public ApiResponse<DamageType> createDamageType(DamageTypeDto damageTypeDto){
        if (damageTypeDto.getName().isEmpty()){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID", null);
        }

        DamageType newDamageType = damageTypeService.save(damageTypeDto);

        return new ApiResponse<>(200, "SUCCESS", newDamageType);
    }

    @PostMapping("/update/{id}")
    public ApiResponse<DamageType> updateDamageType(@PathVariable int id, @RequestBody DamageTypeDto damageTypeDto){
        if (damageTypeDto.getName().isEmpty()){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID", null);
        }

        damageTypeDto.setId(id);
        DamageType updatedDamageType = damageTypeService.update(damageTypeDto);

        if (updatedDamageType != null){
            return new ApiResponse<>(200, "SUCCESS", updatedDamageType);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @GetMapping("/delete/{id}")
    public ApiResponse<Void> deleteDamageType(@PathVariable int id){
        if(damageTypeService.delete(id)){
            return new ApiResponse<>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }
}
