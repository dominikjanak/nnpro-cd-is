package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.PremiseIncident;
import cz.janakdom.backend.model.dto.PremiseIncidentDto;
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

    @PostMapping("/")
    public ApiResponse<PremiseIncident> createPremiseIncident(PremiseIncidentDto premiseIncidentDto) {
        if (premiseIncidentDto.getValid() == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-VALID", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", premiseIncidentService.save(premiseIncidentDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<PremiseIncident> findPremiseIncident(@PathVariable int id) {
        PremiseIncident premiseIncident = premiseIncidentService.findById(id);
        if(premiseIncident != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", premiseIncident);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<PremiseIncident> updatePremiseIncident(@PathVariable int id, @RequestBody PremiseIncidentDto premiseIncidentDto) {
        if (premiseIncidentDto.getValid() == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        PremiseIncident updatedPremiseIncident = premiseIncidentService.update(id, premiseIncidentDto);
        if(updatedPremiseIncident != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedPremiseIncident);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePremiseIncident(@PathVariable int id) {
        if (premiseIncidentService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }
}
