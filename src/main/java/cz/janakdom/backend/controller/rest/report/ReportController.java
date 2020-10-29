package cz.janakdom.backend.controller.rest.report;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Railroad;
import cz.janakdom.backend.model.database.Report;
import cz.janakdom.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/{hash}")
    public String getReport(@PathVariable("hash") String hash, HttpServletResponse response) {
        Report report = reportService.findByHash(hash);

        if (report != null) {
            try {
                response.setHeader("Content-Disposition", "inline;filename=\"" + report.getFilename() + "\"");
                OutputStream out = response.getOutputStream();
                response.setContentType(report.getContentType());
                IOUtils.copy(report.getContent().getBinaryStream(), out);
                out.flush();
                out.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @DeleteMapping("/{hash}")
    public ApiResponse<Void> removeReport(@PathVariable("hash") String hash) {
        Report report = reportService.findByHash(hash);
        if (report != null) {
            reportService.remove(hash);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), report == null ? "BAD_REQUEST" : "SUCCESS", null);
    }

    @PostMapping("/generate")
    public ApiResponse<Railroad> generateReports() {
        boolean generated = reportService.generate();

        return new ApiResponse<>(HttpStatus.OK.value(), generated ? "SUCCESS" : "BAD_REQUEST", null);
    }
}
