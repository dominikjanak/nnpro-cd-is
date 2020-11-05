package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.AttackedSubject;
import cz.janakdom.backend.model.dto.AttackedSubjectDto;
import cz.janakdom.backend.service.AttackedSubjectService;
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
@RequestMapping(value = "/api/attacked-subjects")
public class AttackedSubjectController {

    @Autowired
    private AttackedSubjectService attackedSubjectService;

    @GetMapping("/")
    public ApiResponse<List<AttackedSubject>> listAttackedSubjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubjectService.findAll());
    }

    @PostMapping("/")
    public ApiResponse<AttackedSubject> createAttackedSubject(AttackedSubjectDto attackedSubjectDto) {
        if (attackedSubjectDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        AttackedSubject find = attackedSubjectService.findByName(attackedSubjectDto.getName());

        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubjectService.save(attackedSubjectDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<AttackedSubject> findAttackedSubject(@PathVariable int id) {
        AttackedSubject attackedSubject = attackedSubjectService.findById(id);
        if (attackedSubject != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubject);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<AttackedSubject> updateAttackedSubject(@PathVariable int id, @RequestBody AttackedSubjectDto attackedSubjectDto) {
        if (attackedSubjectDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-NAME", null);
        }

        AttackedSubject updatedAttackedSubject = attackedSubjectService.update(id, attackedSubjectDto);
        if (updatedAttackedSubject != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedAttackedSubject);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAttackedSubject(@PathVariable int id) {
        if (attackedSubjectService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }
}
