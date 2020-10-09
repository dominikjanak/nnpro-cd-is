package cz.upce.nnpro.cd.controller;

import cz.upce.nnpro.cd.model.database.Incident;
import cz.upce.nnpro.cd.model.dto.NewIncident;
import cz.upce.nnpro.cd.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin("*")
public class IncidentController {

    @Autowired
    IncidentService incidentService;

    @GetMapping("/{incidentId}")
    public Incident get(@PathVariable int incidentId) {
        return incidentService.getById(incidentId);
    }

    @GetMapping(value = "/")
    public Page<Incident> getAll(Pageable pageable, @RequestParam(defaultValue = "", required = false) String filter) {
        return incidentService.getAll(pageable, filter);
    }

    @PostMapping("/")
    public Incident add(@RequestBody NewIncident incident) {
        return incidentService.add(incident);
    }

    @PutMapping("/{newIncident}")
    public Incident update(@PathVariable("newIncident") int id, @RequestBody NewIncident newIncident) {
        return incidentService.update(id, newIncident);
    }

    @DeleteMapping("/{newIncident}")
    public void remove(@PathVariable("newIncident") int id) {
        incidentService.remove(id);
    }

}
