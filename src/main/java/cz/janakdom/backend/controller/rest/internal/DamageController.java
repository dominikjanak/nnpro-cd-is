package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Damage;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.dto.DamageDto;
import cz.janakdom.backend.service.DamageService;
import cz.janakdom.backend.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/damages")
public class DamageController {

    @Autowired
    private DamageService damageService;
    @Autowired
    private IncidentService incidentService;

    @GetMapping("/{incidentId}/list")
    public ApiResponse<List<Damage>> findIncidentDamages(@PathVariable int incidentId) {
        Incident incident = incidentService.findById(incidentId);

        if (incident == null || incident.getSecurityIncident() == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        if (incident.getSecurityIncident().getFireIncident() == null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incident.getSecurityIncident().getDamages());
        } else {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", incident.getSecurityIncident().getFireIncident().getDamages());
        }
    }

    @GetMapping("/{incidentId}/{damageId}/one")
    public ApiResponse<Damage> getDamage(@PathVariable int incidentId, @PathVariable int damageId) {
        Damage damage = damageService.findById(damageId);

        if (damageService.validateIncidentId(damage, incidentId)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damage);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/{incidentId}")
    public ApiResponse<Damage> createDamage(@PathVariable int incidentId, @RequestBody DamageDto inputModel) {
        String response = damageService.checkValidity(inputModel);
        if (!response.equals("")) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), response, null);
        }

        Incident incident = incidentService.findById(incidentId);
        if (incident == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        if (incident.getSecurityIncident() == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID-INCIDENT-TYPE", null);
        }

        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageService.addToIncident(incident, inputModel));
        } catch (Exception ex) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        }
    }

    @DeleteMapping("/{incidentId}/{damageId}")
    public ApiResponse<Void> deleteDamage(@PathVariable int incidentId, @PathVariable int damageId) {
        Damage damage = damageService.findById(damageId);

        if (damageService.validateIncidentId(damage, incidentId) && damageService.delete(damage)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }


    @PutMapping("/{incidentId}/{damageId}")
    public ApiResponse<Damage> updateDamage(@PathVariable int incidentId, @PathVariable int damageId, @RequestBody DamageDto inputModel) {
        String response = damageService.checkValidity(inputModel);
        if (!response.equals("")) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), response, null);
        }

        Damage damage = damageService.findById(damageId);

        if (damage == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "DAMAGE-NOT-FOUND", null);
        }

        if (!damageService.validateIncidentId(damage, incidentId)) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "DAMAGE-INVALID-INCIDENT-ID", null);
        }

        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageService.update(damage, inputModel));
        } catch (Exception ex) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        }
    }
}
