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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/railroads")
public class RailroadController {

    @Autowired
    private RailroadService railroadService;

    @GetMapping("/")
    public ApiResponse<List<Railroad>> listInterventionTypes() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", railroadService.findAll());
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
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
