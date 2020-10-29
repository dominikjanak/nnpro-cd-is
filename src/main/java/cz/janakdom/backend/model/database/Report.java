package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "report")
public class Report {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false, length = 64, unique = true)
    private String hash;

    @Getter
    @Setter
    @Column(nullable = false, length = 150, unique = true)
    private String filename;

    @Getter
    @Setter
    @Column(length = 500)
    private String description;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @Setter
    @Lob
    @Column(nullable = false)
    private Blob content;

    @Getter
    @Setter
    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Date created;
}


