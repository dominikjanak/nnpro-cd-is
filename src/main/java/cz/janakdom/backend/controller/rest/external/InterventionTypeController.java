package cz.janakdom.backend.controller.rest.external;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.service.InterventionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/intervention-types")
public class InterventionTypeController {

    @Autowired
    private InterventionTypeService interventionTypeService;

    @GetMapping("/")
    public ApiResponse<Page<InterventionType>> listInterventionTypes(Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", interventionTypeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<InterventionType> findInterventionType(@PathVariable int id) {
        InterventionType interventionType = interventionTypeService.findById(id);

        return new ApiResponse<>(HttpStatus.OK.value(), interventionType == null ? "NOT-EXISTS" : "SUCCESS", interventionType);
    }

    @PostMapping("/reload")
    public ApiResponse<InterventionType> updateInterventionType() {
        boolean updated = interventionTypeService.reload();

        return new ApiResponse<>(HttpStatus.OK.value(), updated ? "SUCCESS" : "BAD_REQUEST", null);
    }
}
