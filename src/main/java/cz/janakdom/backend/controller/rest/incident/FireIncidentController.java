package cz.janakdom.backend.controller.rest.incident;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.dto.incidents.FireIncidentDto;
import cz.janakdom.backend.service.FireIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/fire-incidents")
public class FireIncidentController {

    @Autowired
    private FireIncidentService fireIncidentService;

    @PostMapping("/")
    public ApiResponse<Incident> createFireIncident(@RequestBody FireIncidentDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fireIncidentService.save(inputModel));
    }

    @GetMapping("/{id}")
    public ApiResponse<Incident> findFireIncident(@PathVariable int id) {
        Incident incident = fireIncidentService.getOne(id);

        if (incident == null || incident.getSecurityIncident() == null || incident.getSecurityIncident().getFireIncident() == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incident);
    }

    @PutMapping("/{id}")
    public ApiResponse<Incident> updateSecurityIncident(@PathVariable int id, @RequestBody FireIncidentDto inputmodel) throws Exception {
        Incident updated = fireIncidentService.update(id, inputmodel);

        if (updated == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSecurityIncident(@PathVariable int id) {
        boolean deleted = fireIncidentService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }
}
