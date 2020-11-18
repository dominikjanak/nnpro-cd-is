package cz.janakdom.backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
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
import java.io.IOException;
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

    public int generate() throws DocumentException, SQLException, NoSuchAlgorithmException, IOException {
        // reports with specific name already exists
        LocalDateTime from = setStartDate();
        LocalDateTime to = setEndDate().minusSeconds(1);
        List<Incident> secure = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNullAndCreationDatetimeBetween(from, to);
        List<Incident> fire = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNullAndCreationDatetimeBetween(from, to);

        int res = 0;

        if (reportDao.existsByFilename(getReportName(ReportType.POLICE))) {
            res |= 1;
        } else {
            generatePoliceReport(secure, fire);
            res |= 2;
        }

        if (reportDao.existsByFilename(getReportName(ReportType.HZS))) {
            res |= 4;
        } else {
            generateHZSReport(secure, fire);
            res |= 8;
        }
        return res;
    }

    private void generatePoliceReport(List<Incident> secure, List<Incident> fire) throws DocumentException, SQLException, NoSuchAlgorithmException, IOException {
        Document policeDokument = new Document(PageSize.A4);
        ByteArrayOutputStream policeStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(policeDokument, policeStream);

        BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.NORMAL);
        Font title = new Font(baseFont, 26, Font.NORMAL);
        Font noReport = new Font(baseFont, 16, Font.NORMAL);

        policeDokument.open();

        int reportCounter = 1;
        policeDokument.add(new Paragraph("Policie Report", title));
        policeDokument.add(Chunk.NEWLINE);
        //for police
        //security incidents
        for (Incident incident : secure) {
            SecurityIncident securityIncident = incident.getSecurityIncident();
            if (securityIncident != null) {
                policeDokument.add(new Paragraph("Bezpečnostní incident " + reportCounter, font));
                policeDokument.add(Chunk.NEWLINE);

                policeDokument.add(new Paragraph("Vytvořil: " + incident.getOwner().getFirstname() + " " + incident.getOwner().getSurname(),font));
                policeDokument.add(new Paragraph("Vytvořeno: " + incident.getCreationDatetime(),font));
                policeDokument.add(new Paragraph("Lokace: " + incident.getLocation(),font));
                policeDokument.add(new Paragraph("Region: " + incident.getRegion(),font));
                policeDokument.add(new Paragraph("Poznámka:",font));
                policeDokument.add(new Paragraph(incident.getNote(),font));
                policeDokument.add(Chunk.NEWLINE);
                policeDokument.add(new Paragraph("Popis:",font));
                policeDokument.add(new Paragraph(incident.getDescription(),font));
                if (securityIncident.getCrime())
                    policeDokument.add(new Paragraph("Incident byl klasifikován jako kriminální čin.",font));
                reportCounter++;

                policeDokument.newPage();
            }
        }
        //for police
        //fire incidents
        for (Incident incident : fire) {
            SecurityIncident securityIncident = incident.getSecurityIncident();
            if (securityIncident != null) {
                FireIncident fireIncident = securityIncident.getFireIncident();
                if (fireIncident != null) {
                    if (securityIncident.getPolice()) {
                        policeDokument.add(new Paragraph("Požární incident " + reportCounter, FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK)));
                        policeDokument.add(Chunk.NEWLINE);

                        policeDokument.add(new Paragraph("Vytvořil: " + incident.getOwner().getFirstname() + " " + incident.getOwner().getSurname(),font));
                        policeDokument.add(new Paragraph("Vytvořeno: " + incident.getCreationDatetime(),font));
                        policeDokument.add(new Paragraph("Lokace: " + incident.getLocation(),font));
                        policeDokument.add(new Paragraph("Region: " + incident.getRegion(),font));
                        policeDokument.add(new Paragraph("Poznámka:",font));
                        policeDokument.add(new Paragraph(incident.getNote(),font));
                        policeDokument.add(Chunk.NEWLINE);
                        policeDokument.add(new Paragraph("Popis:",font));
                        policeDokument.add(new Paragraph(incident.getDescription(),font));
                        if (securityIncident.getCrime())
                            policeDokument.add(new Paragraph("Incident byl klasifikován jako kriminální čin.",font));
                        policeDokument.add(new Paragraph("Počet jednotek HZS: " + securityIncident.getFireBrigadeUnits().size(),font));
                        reportCounter++;

                        policeDokument.newPage();
                    }
                }
            }
        }

        if (reportCounter == 1)
            policeDokument.add(new Paragraph("Žádný incident za minulý měsíc.", noReport));
        policeDokument.close();
        saveReport(policeStream, ReportType.POLICE);
    }

    private void generateHZSReport(List<Incident> secure, List<Incident> fire) throws DocumentException, SQLException, NoSuchAlgorithmException, IOException {
        Document hzsDocument = new Document(PageSize.A4);
        ByteArrayOutputStream hzsStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(hzsDocument, hzsStream);

        BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.NORMAL);
        Font title = new Font(baseFont, 26, Font.NORMAL);
        Font noReport = new Font(baseFont, 16, Font.NORMAL);

        hzsDocument.open();

        hzsDocument.add(new Paragraph("HZS Report", title));
        hzsDocument.add(Chunk.NEWLINE);

        int reportCounter = 1;
        for (Incident incident : fire) {
            SecurityIncident securityIncident = incident.getSecurityIncident();
            if (securityIncident != null) {
                FireIncident fireIncident = securityIncident.getFireIncident();
                if (fireIncident != null) {

                    hzsDocument.add(new Paragraph("Požární incident " + reportCounter, font));
                    hzsDocument.add(Chunk.NEWLINE);

                    hzsDocument.add(new Paragraph("Vytvořil: " + incident.getOwner().getFirstname() + " " + incident.getOwner().getSurname(),font));
                    hzsDocument.add(new Paragraph("Vytvořeno: " + incident.getCreationDatetime(),font));
                    hzsDocument.add(new Paragraph("Lokace: " + incident.getLocation(),font));
                    hzsDocument.add(new Paragraph("Region: " + incident.getRegion(),font));
                    hzsDocument.add(new Paragraph("Poznámka:",font));
                    hzsDocument.add(new Paragraph(incident.getNote(),font));
                    hzsDocument.add(Chunk.NEWLINE);
                    hzsDocument.add(new Paragraph("Popis:"));
                    hzsDocument.add(new Paragraph(incident.getDescription(),font));
                    hzsDocument.add(Chunk.NEWLINE);
                    if (securityIncident.getPolice())
                        hzsDocument.add(new Paragraph("Na místě byla přítomna policie.",font));
                    hzsDocument.add(new Paragraph("Počet jednotek HZS: " + securityIncident.getFireBrigadeUnits().size(),font));
                    reportCounter++;

                    hzsDocument.newPage();
                }
            }
        }
        if (reportCounter==1)
            hzsDocument.add(new Paragraph("Žádný incident za minulý měsíc.", noReport));

        hzsDocument.close();
        saveReport(hzsStream, ReportType.HZS);
    }

    private LocalDateTime setStartDate() {
        LocalDateTime ldt = setEndDate().minusMonths(1);
        return ldt;
    }

    private LocalDateTime setEndDate() {
        LocalDateTime ldt = LocalDateTime.now()
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
        String filename = getReportName(reportType);
        report.setFilename(filename);
        report.setHash(md5Generator(filename));

        report.setType(reportType);
        report.setCreated(LocalDateTime.now());
        report.setContentType(".pdf");
        reportDao.save(report);
    }

    private String getReportName(ReportType reportType) {
        return reportType + "_REPORT_" + LocalDateTime.now().getMonth() + "-" + LocalDateTime.now().getYear();
    }

    private String md5Generator(String filename) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(filename.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(digest);
    }

    public boolean delete(String hash) {
        Report report = findByHash(hash);
        if (report == null)
            return false;
        reportDao.delete(report);
        return true;
    }
}
