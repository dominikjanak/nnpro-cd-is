package cz.janakdom.backend.controller.rest.incident;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.dto.incidents.PremiseIncidentDto;
import cz.janakdom.backend.model.dto.incidents.SecurityIncidentDto;
import cz.janakdom.backend.service.IncidentService;
import cz.janakdom.backend.service.PremiseIncidentService;
import cz.janakdom.backend.service.SecurityIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/security-incidents")
public class SecurityIncidentController {

    @Autowired
    private SecurityIncidentService securityIncidentService;

    @PostMapping("/")
    public ApiResponse<Incident> createSecurityIncident(@RequestBody SecurityIncidentDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", securityIncidentService.save(inputModel));
    }

    @GetMapping("/{id}")
    public ApiResponse<Incident> findSecurityIncident(@PathVariable int id) {
        Incident incident = securityIncidentService.getOne(id);

        if (incident == null || incident.getSecurityIncident() == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incident);
    }

    @PutMapping("/{id}")
    public ApiResponse<Incident> updateSecurityIncident(@PathVariable int id, @RequestBody SecurityIncidentDto inputmodel) throws Exception {
        Incident updated = securityIncidentService.update(id, inputmodel);

        if (updated == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSecurityIncident(@PathVariable int id) {
        boolean deleted = securityIncidentService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", null);
    }
}
