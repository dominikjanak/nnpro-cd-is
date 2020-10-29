package cz.janakdom.backend.controller.rest.internalDials;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.dto.carriage.CarriageDto;
import cz.janakdom.backend.model.dto.carriage.CarriageUpdateDto;
import cz.janakdom.backend.service.CarriageService;
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
@RequestMapping(value = "/api/carriages")
public class CarriageController {

    @Autowired
    private CarriageService carriageService;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "25"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    public ApiResponse<Page<Carriage>> listCarriages(@ApiIgnore() Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriageService.findAll(pageable));
    }

    @PostMapping("/")
    public ApiResponse<Carriage> createDamageType(CarriageDto carriageDto) {
        ApiResponse<Carriage> validated = this.validInputCarriageDto(carriageDto);

        if(validated != null) {
            return validated;
        }

        Carriage find = carriageService.findBySerialNumber(carriageDto.getSerialNumber());

        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriageService.save(carriageDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<Carriage> findCarriage(@PathVariable int id) {
        Carriage carriage = carriageService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), carriage == null ? "NOT-EXISTS" : "SUCCESS", carriage);
    }

    @GetMapping("/serial-number/{serialNumber}")
    public ApiResponse<Carriage> findCarriageSerialNumber(@PathVariable String serialNumber) {
        Carriage carriage = carriageService.findBySerialNumber(serialNumber);
        return new ApiResponse<>(HttpStatus.OK.value(), carriage == null ? "NOT-EXISTS" : "SUCCESS", carriage);
    }

    @PutMapping("/{id}")
    public ApiResponse<Carriage> updateCarriage(@PathVariable int id, @RequestBody CarriageUpdateDto carriageDto) {
        Carriage updatedCarriage = carriageService.update(id, carriageDto);
        return new ApiResponse<>(HttpStatus.OK.value(), updatedCarriage == null ? "NOT-FOUND" : "SUCCESS", updatedCarriage);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCarriage(@PathVariable int id) {
        boolean deleted = carriageService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), deleted ? "SUCCESS" : "INVALID", null);
    }

    private ApiResponse<Carriage> validInputCarriageDto(CarriageDto carriageDto) {
        if (carriageDto.getSerialNumber().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-SERIAL-NUMBER", null);
        }
        if (carriageDto.getProducer().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-PRODUCER", null);
        }
        if (carriageDto.getHomeStation().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-HOME-STATION", null);
        }
        return null;
    }
}
