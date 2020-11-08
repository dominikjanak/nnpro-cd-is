package cz.janakdom.backend.controller.rest.external;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.AttackedSubject;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.service.AttackedSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/attacked-entity")
public class AttackedSubjectController {

    @Autowired
    private AttackedSubjectService attackedSubjectService;

    @GetMapping("/")
    public ApiResponse<List<AttackedSubject>> listInterventionTypes() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubjectService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<AttackedSubject> findInterventionType(@PathVariable int id) {
        AttackedSubject attackedSubject = attackedSubjectService.findById(id);
        if (attackedSubject != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubject);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/reload")
    public ApiResponse<Railroad> updateInterventionType() {
        boolean reloaded = attackedSubjectService.reload();
        if (reloaded) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
