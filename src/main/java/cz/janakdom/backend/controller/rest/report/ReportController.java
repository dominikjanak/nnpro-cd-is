package cz.janakdom.backend.controller.rest.report;

import com.itextpdf.text.DocumentException;
import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.model.database.Report;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.report.GenerateReportDto;
import cz.janakdom.backend.model.enums.ReportType;
import cz.janakdom.backend.security.AuthLevel;
import cz.janakdom.backend.service.ReportService;
import cz.janakdom.backend.service.SecurityContext;
import cz.janakdom.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SecurityContext securityContext;

    @Autowired
    private UserService userService;

    @GetMapping("/{type}")
    public ApiResponse<List<Report>> getReportsList(@PathVariable("type") String typeName) {
        ReportType type;

        switch (typeName) {
            case "hzs":
                type = ReportType.HZS;
                break;
            case "police":
                type = ReportType.POLICE;
                break;
            default:
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", reportService.findAll(type));
    }

    @GetMapping("/download/{hash}")
    public String downloadReport(@PathVariable("hash") String hash, HttpServletResponse response) throws IOException, SQLException {
        Report report = reportService.findByHash(hash);
        Blob blobPdf = report.getContent();

        org.apache.commons.io.IOUtils.copy(blobPdf.getBinaryStream(), response.getOutputStream());
        response.addHeader("Content-disposition", "attachment; filename=" + report.getFilename() + report.getContentType());
        response.setContentType("application/pdf");
        response.flushBuffer();

        return null;
    }

    @PostMapping("/generate")
    public ApiResponse<Void> generateReports(@RequestBody GenerateReportDto inputModel)
            throws DocumentException, SQLException, NoSuchAlgorithmException, IOException {
        String token = inputModel.getToken();

        if (token != null && token.equals("K4Cc1TIhe7c5ZcVoavIntxCv213cld5G7w2Y7qKSdosls18yG3d1Kyg683Qo0iZ1")) {
            if (LocalDateTime.now().getDayOfMonth() != 1 && !inputModel.getForce()) {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "NOT-FIRST-DAY-OF-MONTH", null);
            }
            return handleResponse(reportService.generate());
        }
        return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-TOKEN", null);
    }

    @PostMapping("/admin-generate")
    public ApiResponse<Void> adminGenerateReports()
            throws DocumentException, SQLException, NoSuchAlgorithmException, IOException {

        User authenticatedUser = securityContext.getAuthenticatedUser();

        if (authenticatedUser != null && userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return handleResponse(reportService.generate());
        }
        return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
    }

    private ApiResponse<Void> handleResponse(int response) {
        switch (response) {
            case 0:
                return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
            case 1:
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "REPORTS-ALREADY-EXISTS", null);
            default:
                return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "UNKNOWN-ERROR", null);
        }
    }

    @DeleteMapping("/{hash}")
    public ApiResponse<Void> deleteReport(@PathVariable("hash") String hash) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        boolean deleted = reportService.delete(hash);

        if (deleted) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }
}
