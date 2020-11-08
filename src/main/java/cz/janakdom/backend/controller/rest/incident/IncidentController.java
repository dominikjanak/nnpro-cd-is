package cz.janakdom.backend.controller.rest.incident;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.service.IncidentService;
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

    @GetMapping("/")
    public ApiResponse<List<Incident>> findAllIncidents() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAll());
    }

    @GetMapping("/premise")
    public ApiResponse<List<Incident>> findAllPremiseIncidents() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllPremise());
    }

    @GetMapping("/security")
    public ApiResponse<List<Incident>> findAllSecurityIncidents() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllSecurtiy());
    }

    @GetMapping("/fire")
    public ApiResponse<List<Incident>> findAllFireIncidents() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incidentService.getAllFire());
    }
}
