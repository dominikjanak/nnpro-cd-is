package cz.janakdom.backend.controller.rest.internal.read;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Area;
import cz.janakdom.backend.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/")
    public ApiResponse<List<Area>> listRegions() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", areaService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Area> findRegionById(@PathVariable int id) {
        Area area = areaService.findById(id);

        return new ApiResponse<>(HttpStatus.OK.value(), area == null ? "NOT-EXISTS" : "SUCCESS", area);
    }
}
