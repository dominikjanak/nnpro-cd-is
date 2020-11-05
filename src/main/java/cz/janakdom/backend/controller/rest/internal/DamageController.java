package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Damage;
import cz.janakdom.backend.model.dto.DamageDto;
import cz.janakdom.backend.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/damages")
public class DamageController {

    @Autowired
    private DamageService damageService;
/*
    @PostMapping("/")
    public ApiResponse<Damage> createDamage(@RequestBody DamageDto inputModel) throws Exception {
        String response = checkValidity(inputModel);
        if (!response.equals("")) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), response, null);
        }

        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damageService.save(inputModel));
        } catch (Exception ex) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<Damage> findDamage(@PathVariable int id) {
        Damage damage = damageService.findById(id);
        if (damage != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damage);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-EXISTS", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Damage> updateDamage(@PathVariable int id, @RequestBody DamageDto inputModel) {
        String response = checkValidity(inputModel);
        if (!response.equals("")) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), response, null);
        }
        Damage damage = damageService.findById(id);
        if (damage != null) {
            damage = damageService.update(id, inputModel);
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", damage);
        }

        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFireBrigadeUnit(@PathVariable int id) {
        if (damageService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }

    private String checkValidity(DamageDto inputModel) {
        if (inputModel.getFinanceValue() < 0) {
            return "INVALID-FINANCE-VALUE";
        }
        if (inputModel.getAttackedObject().isEmpty()) {
            return "INVALID-ATTACKED-OBJECT";
        }
        if (inputModel.getSecurityIncidentId() != null && inputModel.getFireIncidentId() != null) {
            return "INVALID-PARENTS-BOTH";
        }
        if (inputModel.getSecurityIncidentId() == null && inputModel.getFireIncidentId() == null) {
            return "INVALID-PARENTS-EMPTY";
        }
        return "";
    }*/
}
