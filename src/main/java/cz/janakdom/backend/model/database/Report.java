package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.janakdom.backend.model.enums.ReportType;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
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
}


