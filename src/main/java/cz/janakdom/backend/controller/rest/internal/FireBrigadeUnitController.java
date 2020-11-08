package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.database.FireBrigadeUnit;
import cz.janakdom.backend.model.dto.FireBrigadeUnitDto;
import cz.janakdom.backend.model.dto.carriage.CarriageUpdateDto;
import cz.janakdom.backend.service.FireBrigadeUnitService;
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
@RequestMapping(value = "/api/fire-brigade-units")
public class FireBrigadeUnitController {

    @Autowired
    private FireBrigadeUnitService fireBrigadeUnitService;

    @GetMapping("/")
    public ApiResponse<List<FireBrigadeUnit>> listFireBrigadeUnits() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fireBrigadeUnitService.findAll());
    }

    @PostMapping("/")
    public ApiResponse<FireBrigadeUnit> createFireBrigadeUnit(FireBrigadeUnitDto fireBrigadeUnitDto) {
        if (fireBrigadeUnitDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        FireBrigadeUnit find = fireBrigadeUnitService.findByName(fireBrigadeUnitDto.getName());

        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fireBrigadeUnitService.save(fireBrigadeUnitDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<FireBrigadeUnit> findFireBrigadeUnit(@PathVariable int id) {
        FireBrigadeUnit fireBrigadeUnit = fireBrigadeUnitService.findById(id);
        if(fireBrigadeUnit != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fireBrigadeUnit);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<FireBrigadeUnit> updateFireBrigadeUnit(@PathVariable int id, @RequestBody FireBrigadeUnitDto fireBrigadeUnitDto) {
        if (fireBrigadeUnitDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        FireBrigadeUnit updatedFireBrigadeUnit = fireBrigadeUnitService.update(id, fireBrigadeUnitDto);
        if(updatedFireBrigadeUnit != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedFireBrigadeUnit);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFireBrigadeUnit(@PathVariable int id) {
        if (fireBrigadeUnitService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
