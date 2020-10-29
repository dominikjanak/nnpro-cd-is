package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "premise_incident")
public class PremiseIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date valid;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToOne(mappedBy = "premiseIncident")
    private Incident incident;
}
