package cz.janakdom.backend.controller.rest.internal.read;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/")
    public ApiResponse<List<Region>> listRegions() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", regionService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Region> findRegionById(@PathVariable int id) {
        Region region = regionService.findById(id);

        return new ApiResponse<>(HttpStatus.OK.value(), region == null ? "NOT-EXISTS" : "SUCCESS", region);
    }

    @GetMapping("/abbreviation/{abbreviation}")
    public ApiResponse<Region> findRegionByAbbreviation(@PathVariable String abbreviation) {
        Region region = regionService.findByAbbreviation(abbreviation);

        return new ApiResponse<>(HttpStatus.OK.value(), region == null ? "NOT-EXISTS" : "SUCCESS", region);
    }
}

