package cz.janakdom.backend.controller;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.InterventionType;
import cz.janakdom.backend.model.dto.InterventionTypeDto;
import cz.janakdom.backend.service.InterventionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/interventionType")
public class InterventionTypeController {

    @Autowired
    private InterventionTypeService interventionTypeService;

    @GetMapping("/findAll")
    public ApiResponse<List<InterventionType>> findInterventionTypes(){
        return new ApiResponse<>(200, "SUCCESS", interventionTypeService.findAll());
    }

    @GetMapping("/find/{id}")
    public ApiResponse<InterventionType> findInterventionType(@PathVariable int id){
        InterventionType interventionType = interventionTypeService.findById(id);

        if (interventionType != null){
            return  new ApiResponse<>(200, "SUCCESS", interventionType);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PostMapping("/create")
    public ApiResponse<InterventionType> createInterventionType(InterventionTypeDto interventionTypeDto){
        if (interventionTypeDto.getName().isEmpty()){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID", null);
        }

        InterventionType newInterventionType = interventionTypeService.save(interventionTypeDto);

        return new ApiResponse<>(200, "SUCCESS", newInterventionType);
    }

    @PostMapping("/update/{id}")
    public ApiResponse<InterventionType> updateInterventionType(@PathVariable int id, @RequestBody InterventionTypeDto interventionTypeDto){
        if (interventionTypeDto.getName().isEmpty()){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID", null);
        }

        interventionTypeDto.setId(id);
        InterventionType updatedInterventionType = interventionTypeService.update(interventionTypeDto);

        if (updatedInterventionType != null){
            return new ApiResponse<>(200, "SUCCESS", updatedInterventionType);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @GetMapping("/delete/{id}")
    public ApiResponse<Void> deleteInterventionType(@PathVariable int id){
        if(interventionTypeService.delete(id)){
            return new ApiResponse<>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }
}
