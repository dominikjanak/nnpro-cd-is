package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "premise_incident")
public class PremiseIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Date valid;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @OneToOne(mappedBy = "premiseIncident")
    private Incident incident;
}
