package cz.janakdom.backend.controller.rest.external;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.service.InterventionTypeService;
import cz.janakdom.backend.service.RailroadService;
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
@RequestMapping(value = "/railroads")
public class RailroadController {

    @Autowired
    private RailroadService railroadService;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "25"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    public ApiResponse<Page<Railroad>> listInterventionTypes(@ApiIgnore() Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", railroadService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<Railroad> findInterventionType(@PathVariable int id) {
        Railroad railroad = railroadService.findById(id);

        return new ApiResponse<>(HttpStatus.OK.value(), railroad == null ? "NOT-EXISTS" : "SUCCESS", railroad);
    }

    @PostMapping("/reload")
    public ApiResponse<Railroad> updateInterventionType() {
        boolean reloaded = railroadService.reload();

        return new ApiResponse<>(HttpStatus.OK.value(), reloaded ? "SUCCESS" : "BAD_REQUEST", null);
    }
}
