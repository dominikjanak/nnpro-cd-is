package cz.janakdom.backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import cz.janakdom.backend.dao.IncidentDao;
import cz.janakdom.backend.dao.ReportDao;
import cz.janakdom.backend.model.database.FireIncident;
import cz.janakdom.backend.model.database.Incident;
import cz.janakdom.backend.model.database.Report;
import cz.janakdom.backend.model.database.SecurityIncident;
import cz.janakdom.backend.model.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "reportService")
public class ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private IncidentDao incidentDao;

    public List<Report> findAll(ReportType type) {
        List<Report> list = reportDao.findAllByTypeOrderByIdDesc(type);
        for (Report report : list) {
            report.setContent(null);//problém s návratem blobu
        }
        return list;
    }

    public Report findById(Integer id) {
        Optional<Report> optionalReport = reportDao.findById(id);
        return optionalReport.orElse(null);
    }

    public Report findByHash(String hash) {
        Optional<Report> optionalReport = reportDao.findByHash(hash);
        return optionalReport.orElse(null);
    }

    public boolean generate() throws DocumentException, SQLException, NoSuchAlgorithmException {
        List<Incident> secure = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNullAndCreationDatetimeBetween(setStartDate(), setEndDate());
        List<Incident> fire = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNullAndCreationDatetimeBetween(setStartDate(), setEndDate());

        Document hzsDocument = new Document(PageSize.A4);
        Document policeDokument = new Document(PageSize.A4);

        ByteArrayOutputStream hzsStream = new ByteArrayOutputStream();
        ByteArrayOutputStream policeStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(hzsDocument, hzsStream);
        PdfWriter.getInstance(policeDokument, policeStream);

        hzsDocument.open();
        policeDokument.open();

        hzsDocument.add(new Paragraph("HZS Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 28, BaseColor.BLACK)));
        hzsDocument.add(Chunk.NEWLINE);

        policeDokument.add(new Paragraph("Policie Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 28, BaseColor.BLACK)));
        policeDokument.add(Chunk.NEWLINE);

        //for HZS
        int reportCounter = 1;
        for (Incident incident : fire) {
            SecurityIncident securityIncident = incident.getSecurityIncident();
            if (securityIncident != null) {
                FireIncident fireIncident = securityIncident.getFireIncident();
                if (fireIncident != null) {

                    hzsDocument.add(new Paragraph("Report " + reportCounter, FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK)));
                    hzsDocument.add(Chunk.NEWLINE);

                    hzsDocument.add(new Paragraph("Vytvoril: " + incident.getOwner().getFirstname() + " " + incident.getOwner().getSurname()));
                    hzsDocument.add(new Paragraph("Vytvoreno: " + incident.getCreationDatetime()));
                    hzsDocument.add(new Paragraph("Lokace: " + incident.getLocation()));
                    hzsDocument.add(new Paragraph("Region: " + incident.getRegion()));
                    hzsDocument.add(new Paragraph("Poznamka:"));
                    hzsDocument.add(new Paragraph(incident.getNote()));
                    hzsDocument.add(Chunk.NEWLINE);
                    hzsDocument.add(new Paragraph("Popis:"));
                    hzsDocument.add(new Paragraph(incident.getDescription()));
                    hzsDocument.add(Chunk.NEWLINE);
                    if (securityIncident.getPolice())
                        hzsDocument.add(new Paragraph("Na miste byla pritomna policie."));
                    hzsDocument.add(new Paragraph("Pocet jednotek HZS: " + securityIncident.getFireBrigadeUnits().size()));
                    reportCounter++;

                    hzsDocument.newPage();
                }
            }
        }

        //for police
        reportCounter = 1;
        for (Incident incident : secure) {
            SecurityIncident securityIncident = incident.getSecurityIncident();
            if (securityIncident != null) {
                policeDokument.add(new Paragraph("Report " + reportCounter, FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK)));
                policeDokument.add(Chunk.NEWLINE);

                policeDokument.add(new Paragraph("Vytvoril: " + incident.getOwner().getFirstname() + " " + incident.getOwner().getSurname()));
                policeDokument.add(new Paragraph("Vytvoreno: " + incident.getCreationDatetime()));
                policeDokument.add(new Paragraph("Lokace: " + incident.getLocation()));
                policeDokument.add(new Paragraph("Region: " + incident.getRegion()));
                policeDokument.add(new Paragraph("Poznamka:"));
                policeDokument.add(new Paragraph(incident.getNote()));
                policeDokument.add(Chunk.NEWLINE);
                policeDokument.add(new Paragraph("Popis:"));
                policeDokument.add(new Paragraph(incident.getDescription()));
                if (securityIncident.getCrime())
                    policeDokument.add(new Paragraph("Incident byl klasifikovan jako kriminalni cin."));
                reportCounter++;

                policeDokument.newPage();
            }
        }

        hzsDocument.close();
        policeDokument.close();
        saveReport(hzsStream, ReportType.HZS);
        saveReport(policeStream, ReportType.POLICE);
        return true;
    }

    private LocalDateTime setStartDate() {
        int mesic;
        if (LocalDateTime.now().getMonthValue() == 1)
            mesic = 12;
        else
            mesic = LocalDateTime.now().getMonthValue() - 1;

        LocalDateTime ldt = LocalDateTime.now()
                .withYear(LocalDateTime.now().getYear())
                .withMonth(mesic)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return ldt;
    }
    private LocalDateTime setEndDate() {
        LocalDateTime ldt = LocalDateTime.now()
                .withYear(LocalDateTime.now().getYear())
                .withMonth(LocalDateTime.now().getMonthValue())
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return ldt;
    }

    private void saveReport(ByteArrayOutputStream stream, ReportType reportType) throws SQLException, NoSuchAlgorithmException {
        Report report = new Report();
        Blob blob = new SerialBlob(stream.toByteArray());
        report.setContent(blob);
        String filename = reportType + "_REPORT_" + LocalDateTime.now().getMonth() + "-" + LocalDateTime.now().getYear();
        report.setFilename(filename);
        report.setHash(md5Generator(filename));

        report.setType(reportType);
        report.setCreated(LocalDateTime.now());
        report.setContentType(".pdf");
        reportDao.save(report);
    }

    private String md5Generator(String filename) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(filename.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(digest);
    }
}
