package cz.janakdom.backend.controller.rest.crud;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import cz.janakdom.backend.service.DamageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/damage-types")
public class DamageTypeController {

    @Autowired
    private DamageTypeService damageTypeService;

    @GetMapping("/")
    public ApiResponse<Page<DamageType>> listDamageTypes(Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageTypeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<DamageType> findDamageType(@PathVariable int id) {
        DamageType damageType = damageTypeService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), damageType == null ? "NOT-EXISTS" : "SUCCESS", damageType);
    }

    @PostMapping("/")
    public ApiResponse<DamageType> createDamageType(DamageTypeDto damageTypeDto) {
        if (damageTypeDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-NAME", null);
        }

        if (damageTypeService.findByName(damageTypeDto.getName()) != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageTypeService.save(damageTypeDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<DamageType> updateDamageType(@PathVariable int id, @RequestBody DamageTypeDto damageTypeDto) {
        if (damageTypeDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-NAME", null);
        }

        DamageType updatedDamageType = damageTypeService.update(id, damageTypeDto);
        return new ApiResponse<>(HttpStatus.OK.value(), updatedDamageType == null ? "NOT-FOUND" : "SUCCESS", updatedDamageType);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDamageType(@PathVariable int id) {
        boolean deleted = damageTypeService.delete(id);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), deleted ? "SUCCESS" : "INVALID", null);
    }
}
