package cz.janakdom.backend.controller.rest.report;

import com.itextpdf.text.DocumentException;
import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.model.database.Report;
import cz.janakdom.backend.model.enums.ReportType;
import cz.janakdom.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

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
    public ApiResponse<Railroad> generateReports() throws DocumentException, SQLException, NoSuchAlgorithmException {
        boolean generated = reportService.generate();

        if (generated) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID-REPORT-TYPE", null);
    }
}
