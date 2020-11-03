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

@CrossOrigin
@RestController
@RequestMapping(value = "/api/attacked-subjects")
public class AttackedSubjectController {

    @Autowired
    private AttackedSubjectService attackedSubjectService;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "25"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
    })
    public ApiResponse<Page<AttackedSubject>> listAttackedSubjects(@ApiIgnore() Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubjectService.findAll(pageable));
    }

    @PostMapping("/")
    public ApiResponse<AttackedSubject> createAttackedSubject(AttackedSubjectDto attackedSubjectDto) {
        if (attackedSubjectDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-NAME", null);
        }

        AttackedSubject find = attackedSubjectService.findByName(attackedSubjectDto.getName());

        if (find != null && !find.getIsDeleted()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", attackedSubjectService.save(attackedSubjectDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<AttackedSubject> findAttackedSubject(@PathVariable int id) {
        AttackedSubject attackedSubject = attackedSubjectService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), attackedSubject == null ? "NOT-EXISTS" : "SUCCESS", attackedSubject);
    }

    @PutMapping("/{id}")
    public ApiResponse<AttackedSubject> updateAttackedSubject(@PathVariable int id, @RequestBody AttackedSubjectDto attackedSubjectDto) {
        if (attackedSubjectDto.getName().isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "EMPTY-NAME", null);
        }

        AttackedSubject updatedAttackedSubject = attackedSubjectService.update(id, attackedSubjectDto);
        return new ApiResponse<>(HttpStatus.OK.value(), updatedAttackedSubject == null ? "NOT-FOUND" : "SUCCESS", updatedAttackedSubject);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAttackedSubject(@PathVariable int id) {
        boolean deleted = attackedSubjectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), deleted ? "SUCCESS" : "INVALID", null);
    }
}
