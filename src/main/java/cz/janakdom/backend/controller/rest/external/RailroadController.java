package cz.janakdom.backend.controller.rest.external;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Railroad;
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
@RequestMapping(value = "/api/railroads")
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
        if (railroad != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", railroad);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/reload")
    public ApiResponse<Railroad> updateInterventionType() {
        boolean reloaded = railroadService.reload();
        if (reloaded) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }
}
