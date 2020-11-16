package cz.janakdom.backend.model.database;

import cz.janakdom.backend.model.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64, unique = true)
    private String hash;

    @Column(nullable = false, length = 150, unique = true)
    private String filename;

    @Column(nullable = false)
    private ReportType type;

    @Lob
    @Column(nullable = false)
    private Blob content;

    // application/pdf
    @Column(nullable = false)
    private String contentType;

    // LocalDateTime
    // created = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getFilename() {
        return filename;
    }

    public ReportType getType() {
        return type;
    }

    public Blob getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}


