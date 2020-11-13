package cz.janakdom.backend.controller.rest.internal.read;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Area;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.service.RegionService;
import cz.janakdom.backend.service.SecurityContext;
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

    @Autowired
    private SecurityContext securityContext;

    @GetMapping("/")
    public ApiResponse<List<Region>> listRegions() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", regionService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Region> findRegionById(@PathVariable int id) {
        Region region = regionService.findById(id);
        if (region != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", region);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @GetMapping("/my")
    public ApiResponse<List<Region>> findUsersRegionById() {
        User user = securityContext.getAuthenticatedUser();
        if (user != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", user.getArea().getRegions());
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }

    @GetMapping("/abbreviation/{abbreviation}")
    public ApiResponse<Region> findRegionByAbbreviation(@PathVariable String abbreviation) {
        Region region = regionService.findByAbbreviation(abbreviation);
        if (region != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }
}

