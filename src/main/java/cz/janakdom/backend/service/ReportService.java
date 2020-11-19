package cz.janakdom.backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import cz.janakdom.backend.dao.IncidentDao;
import cz.janakdom.backend.dao.ReportDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.enums.IncidentType;
import cz.janakdom.backend.model.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "reportService")
public class ReportService {

    private final Font normal;
    private final Font bold;
    private final Font header;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private IncidentDao incidentDao;

    public ReportService() throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250, BaseFont.EMBEDDED);
        normal = new Font(baseFont, 11, Font.NORMAL);
        bold = new Font(baseFont, 11, Font.BOLD);
        header = new Font(baseFont, 24, Font.BOLD);
    }

    public List<Report> findAll(ReportType type) {
        return reportDao.findAllByTypeOrderByIdDesc(type);
    }

    public Report findById(Integer id) {
        Optional<Report> optionalReport = reportDao.findById(id);
        return optionalReport.orElse(null);
    }

    public Report findByHash(String hash) {
        Optional<Report> optionalReport = reportDao.findByHash(hash);
        return optionalReport.orElse(null);
    }

    public boolean delete(String hash) {
        Report report = findByHash(hash);
        if (report == null)
            return false;
        reportDao.delete(report);
        return true;
    }

    public int generate() throws Exception {
        // reports with specific name already exists
        LocalDateTime from = setStartDate();
        LocalDateTime to = setEndDate().minusSeconds(1);
        List<Incident> secure = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNullAndCreationDatetimeBetween(from, to);
        List<Incident> fire = incidentDao.findAllBySecurityIncidentIsNotNullAndPremiseIncidentIsNullAndSecurityIncidentFireIncidentIsNotNullAndCreationDatetimeBetween(from, to);
        List<Incident> incidents = new ArrayList<>();
        incidents.addAll(secure);
        incidents.addAll(fire);

        int res = 0;
        if (reportDao.existsByFilename(getReportName(ReportType.POLICE, from.getMonth()))) {
            res |= 1;
        } else {
            generateReport(incidents, ReportType.POLICE, from.getMonth());
            res |= 2;
        }

        if (reportDao.existsByFilename(getReportName(ReportType.HZS, from.getMonth()))) {
            res |= 4;
        } else {
            generateReport(incidents, ReportType.HZS, from.getMonth());
            res |= 8;
        }
        return res;
    }

    private void generateReport(List<Incident> incidents, ReportType reportType, Month month)
            throws Exception {

        Document report = new Document(PageSize.A4);
        ByteArrayOutputStream reportStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(report, reportStream);

        report.open();
        int incidentCounter = 0;
        double totalSum = 0;
        SecurityIncident si = null;
        FireIncident fi = null;

        for (Incident i : incidents) {
            IncidentType incidentType = getIncidentType(i);

            if (incidentType != IncidentType.FIRE && incidentType != IncidentType.SECURITY) {
                throw new Exception("INVALID-INCIDENT-" + i.getId());
            }

            si = i.getSecurityIncident();
            fi = si.getFireIncident();
            if ((reportType == ReportType.POLICE && (si.getPolice() || si.getDamages().size()>0)) // pro policii
                    || (reportType == ReportType.HZS && (incidentType == IncidentType.FIRE || si.getFireBrigadeUnits().size()>0))) // pro hasiče
            {
                // GENERATE
                incidentCounter++;

                String title = getincidentTitle(reportType, incidentType, incidentCounter);
                totalSum += generateIncidentPage(report, title, incidentType, i, si, fi);
                //END of GENERATE
            }
        }
        if (incidentCounter > 0) {
            report.newPage();
            documentAddLine(report, "Úhrn celkového poškození: ", nf(totalSum) + " Kč");
        } else {
            documentAddLine(report, "Žádný incidencident.", null);
        }

        report.close();
        saveReport(reportStream, reportType, month);
    }


    private double generateIncidentPage(Document report, String title, IncidentType incidentType,
                                        Incident i, SecurityIncident si, FireIncident fi)
            throws DocumentException {

        report.add(new Paragraph(title, header));
        documentAddNewLine(report);
        documentAddLine(report, "Vytvořil: ", i.getOwner().getFullName() + " (" + i.getOwner().getEmail() + ")");
        documentAddLine(report, "Zodpovídá: ", si.getManager().getFullName() + " (" + si.getManager().getEmail() + ")");
        documentAddLine(report, "Vytvořeno: ", dateFormat(i.getCreationDatetime()));
        if (incidentType == IncidentType.FIRE) {
            documentAddLine(report, "Doba zásahu: ", dateFormat(fi.getValidFrom()) + " - " + dateFormat(fi.getValidTo()));
            documentAddLine(report, "Typ zásahu: ", fi.getInterventionType().getName());
        }
        documentAddLine(report, "Region: ", i.getRegion().getName());
        documentAddLine(report, "Lokace: ", i.getLocation());
        documentAddLine(report, "Trať: ", si.getRailroad().getNumber() + ", " + si.getRailroad().getName());
        Carriage cr = si.getCarriage();
        documentAddLine(report, "Vůz: ", cr.getSerialNumber() + " " + cr.getProducer() + "-" + cr.getColor() + " (" + si.getCarriage().getHomeStation() + ")");
        documentAddLine(report, "Popis: ", i.getDescription(), true);
        if(!i.getNote().equals(""))
            documentAddLine(report, "Poznámka: ", i.getNote(), true);

        List<FireBrigadeUnit> fireBrigadeUnits = si.getFireBrigadeUnits();
        StringBuilder fireBridgeUnitsString = new StringBuilder();
        for (FireBrigadeUnit fireBrigadeUnit : fireBrigadeUnits) {
            if (fireBridgeUnitsString.length() > 0)
                fireBridgeUnitsString.append(", ");
            fireBridgeUnitsString.append(fireBrigadeUnit.getName());
        }
        documentAddLine(report, "Jednotky HZS: ", fireBridgeUnitsString.toString());

        List<AttackedSubject> attackedSubjects = si.getAttackedSubjects();
        StringBuilder attackedSubjectsString = new StringBuilder();
        for (AttackedSubject attackedSubject : attackedSubjects) {
            if (attackedSubjectsString.length() > 0)
                attackedSubjectsString.append(", ");
            attackedSubjectsString.append(attackedSubject.getName());
        }
        documentAddLine(report, "Poškozené subjekty: ", attackedSubjectsString.toString());

        documentAddNewLine(report);
        if (si.getPolice())
            documentAddLine(report, "Na místě byla přítomna policie.", null);
        if (si.getCrime())
            documentAddLine(report, "Incident byl klasifikován jako kriminální čin.", null);
        if (si.getChecked())
            documentAddLine(report, "Incident byl zkontrolován.", null);
        documentAddNewLine(report);

        List<Damage> damages = incidentType == IncidentType.FIRE ? fi.getDamages() : si.getDamages();
        double damageSum = 0;
        documentAddLine(report, "Poškození:", null);
        for (Damage damage : damages) {
            documentAddLine(report, "      ", damage.getAttackedObject() + " - " + damage.getDamageType().getName() + " - " + nf(damage.getFinanceValue()) + " Kč");
            damageSum += damage.getFinanceValue();
        }

        documentAddLine(report, "Poškození celkem za: ", nf(damageSum) + " Kč");
        report.newPage();
        return damageSum;
    }

    private void saveReport(ByteArrayOutputStream stream, ReportType reportType, Month month)
            throws SQLException, NoSuchAlgorithmException {
        Report report = new Report();
        Blob blob = new SerialBlob(stream.toByteArray());
        report.setContent(blob);
        String filename = getReportName(reportType, month);
        report.setFilename(filename);
        report.setHash(md5Generator(filename));

        report.setType(reportType);
        report.setCreated(LocalDateTime.now());
        report.setContentType("application/pdf");
        reportDao.save(report);
    }

    private IncidentType getIncidentType(Incident incident) {
        if (incident.getPremiseIncident() != null
                && incident.getSecurityIncident() == null) {
            return IncidentType.PREMISE;
        }

        SecurityIncident si = incident.getSecurityIncident();

        if (si == null || si.getIncident().getPremiseIncident() != null) {
            return IncidentType.INVALID;
        }

        if (si.getFireIncident() == null) {
            return IncidentType.SECURITY;
        }

        if (si.getFireIncident() != null) {
            return IncidentType.FIRE;
        }

        return IncidentType.INVALID;
    }

    private void documentAddLine(Document report, String title, String value) throws DocumentException {
        documentAddLine(report, title, value, false);
    }

    private void documentAddLine(Document report, String title, String value, boolean newline) throws DocumentException {
        Paragraph p = new Paragraph();
        if (title != null)
            p.add(new Phrase(title, bold));

        if (newline)
            p.add(Chunk.NEWLINE);

        if (value != null)
            p.add(new Phrase(value, normal));
        report.add(p);
    }

    private void documentAddNewLine(Document report) throws DocumentException {
        documentAddLine(report, null, " ");
    }

    private String getincidentTitle(ReportType reportType, IncidentType incidentType, int cnt) {
        StringBuilder title = new StringBuilder();

        if (reportType == ReportType.HZS) {
            title.append("Report HZS: ");
        } else {
            title.append("Report policii: ");
        }
        if (incidentType == IncidentType.SECURITY) {
            title.append("bezpečnostní - ");
        } else if (incidentType == IncidentType.FIRE) {
            title.append("požární - ");
        }

        title.append(cnt);

        return title.toString();
    }

    private String getReportName(ReportType reportType, Month time) {
        return reportType + "-REPORT-" + monthLabel(time) + "-" + LocalDateTime.now().getYear();
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

    private String dateFormat(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm:ss");
        return datetime.format(formatter);
    }

    private String nf(double number) {
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number).replace(",", " ").replace(".", ",");
    }

    private String md5Generator(String filename) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(filename.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(digest);
    }

    private String monthLabel(Month month) {
        switch (month) {
            case JANUARY:
                return "LEDEN";
            case FEBRUARY:
                return "UNOR";
            case MARCH:
                return "BREZEN";
            case APRIL:
                return "DUBEN";
            case MAY:
                return "KVETEN";
            case JUNE:
                return "CERVEN";
            case JULY:
                return "CERVENEC";
            case AUGUST:
                return "SRPEN";
            case SEPTEMBER:
                return "ZARI";
            case OCTOBER:
                return "RIJEN";
            case NOVEMBER:
                return "LISTOPAD";
            case DECEMBER:
                return "PROSINEC";
        }
        return "";
    }
}
