package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.ReportDao;
import cz.janakdom.backend.model.database.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "reportService")
public class ReportService {

    @Autowired
    private ReportDao reportDao;

    public List<Report> findAll() {
        return reportDao.findAllByOrderByIdDesc();
    }

    public Report findById(Integer id) {
        Optional<Report> optionalReport = reportDao.findById(id);
        return optionalReport.orElse(null);
    }

    public Report findByHash(String hash) {
        Optional<Report> optionalReport = reportDao.findByHash(hash);
        return optionalReport.orElse(null);
    }

    public boolean generate() {
        // TODO: generate all reports
        // report store in DB

        return false;
    }
}
