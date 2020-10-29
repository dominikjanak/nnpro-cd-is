package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Column(nullable = false, length = 64, unique = true)
    private String hash;

    @Column(nullable = false, length = 150, unique = true)
    private String filename;

    @Column(length = 500)
    private String description;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Lob
    @Column(nullable = false)
    private Blob content;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Date created;
}


