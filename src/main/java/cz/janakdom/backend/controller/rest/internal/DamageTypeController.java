package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.DamageType;
import cz.janakdom.backend.model.dto.DamageTypeDto;
import cz.janakdom.backend.service.DamageTypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/damage-types")
public class DamageTypeController {

    @Autowired
    private DamageTypeService damageTypeService;

    @GetMapping("/")
    public ApiResponse<List<DamageType>> listDamageTypes() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<DamageType> findDamageType(@PathVariable int id) {
        DamageType damageType = damageTypeService.findById(id);
        if (damageType != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageType);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/")
    public ApiResponse<DamageType> createDamageType(DamageTypeDto damageTypeDto) {
        if (damageTypeDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        DamageType find = damageTypeService.findByName(damageTypeDto.getName());
        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageTypeService.save(damageTypeDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<DamageType> updateDamageType(@PathVariable int id, @RequestBody DamageTypeDto damageTypeDto) {
        if (damageTypeDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        DamageType updatedDamageType = damageTypeService.update(id, damageTypeDto);
        if (updatedDamageType != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedDamageType);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDamageType(@PathVariable int id) {
        if (damageTypeService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
