package cz.janakdom.backend.controller.rest.building;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.*;
import cz.janakdom.backend.model.dto.building.BuildingDto;
import cz.janakdom.backend.model.dto.building.BuildingUpdateDto;
import cz.janakdom.backend.model.dto.incidents.SecurityIncidentDto;
import cz.janakdom.backend.model.dto.report.GenerateReportDto;
import cz.janakdom.backend.model.enums.ReportType;
import cz.janakdom.backend.security.AuthLevel;
import cz.janakdom.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/building")
public class BuildingController {

    @Autowired
    private FileService fileService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private EPSService epsService;
    @Autowired
    private FireExtinguisherService fireExtinguisherService;
    @Autowired
    private HydrantService hydrantService;
    @Autowired
    private TechnicalSystemService technicalSystemService;
    @Autowired
    private TelNumberService telNumberService;

    @PostMapping("/")
    public ApiResponse<Building> createBuilding(@RequestBody BuildingDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", buildingService.save(inputModel));
    }

    @GetMapping("/{id}")
    public ApiResponse<Building> findBuilding(@PathVariable int id) {
        Building building = buildingService.findById(id);

        if (building == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", building);
    }

    @GetMapping("/")
    public ApiResponse<List<Building>> findAllBuildings() {
        List<Building> buildings = buildingService.findAllNotDeleted();

        if (buildings.size()==0) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", buildings);
    }

    @PutMapping("/{id}")
    public ApiResponse<Building> updateBuilding(@PathVariable int id, @RequestBody BuildingUpdateDto inputmodel) throws Exception {
        Building updated = buildingService.update(id, inputmodel);

        if (updated == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBuilding(@PathVariable int id) {
        boolean deleted = buildingService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/file/upload")
    public ApiResponse<FileDB> uploadFile(@RequestParam int buildingId, @RequestParam("file") MultipartFile file) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fileService.save(file, buildingId));
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-FILE", null);
        }
    }

    @GetMapping("/file/list/{buildingId}")
    public ApiResponse<List<ResponseFile>> getListFiles(@PathVariable Integer buildingId) {
        List<ResponseFile> files = fileService.getAllFilesByBuildingIdStream(buildingId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/building/file/download1/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();
            ResponseFile responseFile = new ResponseFile();
            responseFile.setId(dbFile.getId());
            responseFile.setFilename(dbFile.getFilename());
            responseFile.setUrl(fileDownloadUri);
            responseFile.setContentType(dbFile.getContentType());
            return responseFile;
        }).collect(Collectors.toList());

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", files);
    }

    @GetMapping("/file/download1/{fileId}")//funkcni
    public ResponseEntity<byte[]> downloadFileV1(@PathVariable Integer fileId) {
        FileDB fileDB = fileService.getFile(fileId);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFilename() + "\"")
                    .body(fileDB.getContent().getBytes(1L, (int)fileDB.getContent().length()));
        } catch (SQLException throwables) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/file/download2/{id}")//pravdepodobne nefunguje
    public String downloadFileV2(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException, SQLException {
        FileDB fileDB = fileService.getFile(id);
        Blob blobFile = fileDB.getContent();

        org.apache.commons.io.IOUtils.copy(blobFile.getBinaryStream(), response.getOutputStream());
        response.addHeader("Content-disposition", "attachment; filename=" + fileDB.getFilename() /*+ fileDB.getContentType()*/);
        response.setContentType(fileDB.getContentType());
        response.flushBuffer();

        return null;
    }

    @DeleteMapping("/file/{id}")
    public ApiResponse<Void> deleteFile(@PathVariable int id) {
        boolean deleted = fileService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/eps/")
    public ApiResponse<EPS> createEps(@RequestBody EPSDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", epsService.save(inputModel));
    }

    @DeleteMapping("/eps/{id}")
    public ApiResponse<Void> deleteEps(@PathVariable int id) {
        boolean deleted = epsService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/extinguisher/")
    public ApiResponse<FireExtinguisher> createFireExtingusher(@RequestBody FireExtinguisherDto inputModel) throws Exception {
        FireExtinguisher extinguisher = fireExtinguisherService.save(inputModel);
        if (extinguisher != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", fireExtinguisherService.save(inputModel));
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "PROBABLY-BAD-TYPE", null);
    }

    @DeleteMapping("/extinguisher/{id}")
    public ApiResponse<Void> deleteFireExtingusher(@PathVariable int id) {
        boolean deleted = fireExtinguisherService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/hydrant/")
    public ApiResponse<Hydrant> createHydrant(@RequestBody HydrantDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", hydrantService.save(inputModel));
    }

    @DeleteMapping("/hydrant/{id}")
    public ApiResponse<Void> deleteHydrant(@PathVariable int id) {
        boolean deleted = hydrantService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/technical/")
    public ApiResponse<TechnicalSystem> createTechnicalSystem(@RequestBody TechnicalSystemDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", technicalSystemService.save(inputModel));
    }

    @DeleteMapping("/technical/{id}")
    public ApiResponse<Void> deleteTechnicalSystem(@PathVariable int id) {
        boolean deleted = technicalSystemService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @PostMapping("/number/")
    public ApiResponse<TelNumber> createTelNumber(@RequestBody TelNumberDto inputModel) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", telNumberService.save(inputModel));
    }

    @DeleteMapping("/number/{id}")
    public ApiResponse<Void> deleteTelNumber(@PathVariable int id) {
        boolean deleted = telNumberService.delete(id);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

}





