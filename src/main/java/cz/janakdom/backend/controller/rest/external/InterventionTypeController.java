package cz.janakdom.backend.controller.rest.external;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.service.InterventionTypeService;
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
@RequestMapping(value = "/api/intervention-types")
public class InterventionTypeController {

    @Autowired
    private InterventionTypeService interventionTypeService;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "25"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    public ApiResponse<Page<InterventionType>> listInterventionTypes(@ApiIgnore() Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", interventionTypeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<InterventionType> findInterventionType(@PathVariable int id) {
        InterventionType interventionType = interventionTypeService.findById(id);
        if (interventionType != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", interventionType);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/reload")
    public ApiResponse<InterventionType> updateInterventionType() {
        boolean reloaded = interventionTypeService.reload();
        if (reloaded) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }
}
