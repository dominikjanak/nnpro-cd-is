package cz.janakdom.backend.controller.rest.incident;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.service.IncidentService;
import cz.janakdom.backend.service.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private SecurityContext securityContext;

    @GetMapping("/")
    public ApiResponse<List<Incident>> findAllIncidents() throws Exception {
        List<Region> regions = getUserRegions();
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAll(regions));
    }

    @GetMapping("/premise")
    public ApiResponse<List<Incident>> findAllPremiseIncidents() throws Exception {
        List<Region> regions = getUserRegions();
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllPremise(regions));
    }

    @GetMapping("/security")
    public ApiResponse<List<Incident>> findAllSecurityIncidents() throws Exception {
        List<Region> regions = getUserRegions();
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllSecurtiy(regions));
    }

    @GetMapping("/fire")
    public ApiResponse<List<Incident>> findAllFireIncidents() throws Exception {
        List<Region> regions = getUserRegions();
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllFire(regions));
    }

    private List<Region> getUserRegions() throws Exception {
        User user = securityContext.getAuthenticatedUser();
        if(user == null) {
            throw new Exception("BAD-REQUEST");
        }

        return user.getArea().getRegions();
    }
}
