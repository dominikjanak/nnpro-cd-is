package cz.janakdom.backend.controller.rest.incident;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.dto.incidents.PremiseIncidentDto;
import cz.janakdom.backend.service.IncidentService;
import cz.janakdom.backend.service.PremiseIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/premise-incidents")
public class PremiseIncidentController {

    @Autowired
    private PremiseIncidentService premiseIncidentService;
    @Autowired
    private IncidentService incidentService;

    @PostMapping("/")
    public ApiResponse<Incident> createPremiseIncident(@RequestBody PremiseIncidentDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", premiseIncidentService.save(inputModel));
    }

    @GetMapping("/{id}")
    public ApiResponse<Incident> findPremiseIncident(@PathVariable int id) {
        Incident incident = premiseIncidentService.getOne(id);

        if (incident == null || incident.getPremiseIncident() == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incident);
    }

    @PutMapping("/{id}")
    public ApiResponse<Incident> updatePremiseIncident(@PathVariable int id, @RequestBody PremiseIncidentDto inputmodel) {
        Incident updated = premiseIncidentService.update(id, inputmodel);

        if (updated == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePremiseIncident(@PathVariable int id) {
        boolean deleted = incidentService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", null);
    }
}
