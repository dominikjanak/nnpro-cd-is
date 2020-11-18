package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Blob content;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

}


