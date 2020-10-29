package cz.janakdom.backend.controller.rest.crud;

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

@CrossOrigin
@RestController
@RequestMapping(value = "/damage-types")
public class DamageTypeController {

    @Autowired
    private DamageTypeService damageTypeService;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "25"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    public ApiResponse<Page<DamageType>> listDamageTypes(@ApiIgnore() Pageable pageable) {
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

        DamageType find = damageTypeService.findByName(damageTypeDto.getName());
        if (find != null && !find.getIsDeleted()) {
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
