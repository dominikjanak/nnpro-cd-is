package cz.janakdom.backend.controller.rest.internal;

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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/carriages")
public class CarriageController {

    @Autowired
    private CarriageService carriageService;

    @GetMapping("/")
    public ApiResponse<List<Carriage>> listCarriages() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriageService.findAll());
    }

    @PostMapping("/")
    public ApiResponse<Carriage> createCarriage(@RequestBody CarriageDto carriageDto) {
        if (carriageDto.getSerialNumber() == null || carriageDto.getSerialNumber().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-SERIAL-NUMBER", null);
        }
        if (carriageDto.getProducer() == null || carriageDto.getProducer().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-PRODUCER", null);
        }
        if (carriageDto.getHomeStation() == null || carriageDto.getHomeStation().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-HOME-STATION", null);
        }

        Carriage find = carriageService.findBySerialNumber(carriageDto.getSerialNumber());
        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriageService.save(carriageDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<Carriage> findCarriage(@PathVariable int id) {
        Carriage carriage = carriageService.findById(id);
        if (carriage != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriage);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @GetMapping("/serial-number/{serialNumber}")
    public ApiResponse<Carriage> findCarriageSerialNumber(@PathVariable String serialNumber) {
        Carriage carriage = carriageService.findBySerialNumber(serialNumber);
        if (carriage != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", carriage);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Carriage> updateCarriage(@PathVariable int id, @RequestBody CarriageUpdateDto carriageDto) {
        if (carriageDto.getProducer() == null || carriageDto.getProducer().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-PRODUCER", null);
        }
        if (carriageDto.getHomeStation() == null || carriageDto.getHomeStation().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-HOME-STATION", null);
        }

        Carriage updatedCarriage = carriageService.update(id, carriageDto);
        if (updatedCarriage != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedCarriage);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCarriage(@PathVariable int id) {
        if (carriageService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
