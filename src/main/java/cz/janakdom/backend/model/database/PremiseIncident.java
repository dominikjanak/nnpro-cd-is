package cz.janakdom.backend.model.database;

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
    @OneToOne(mappedBy = "premiseIncident")
    private Incident incident;
}
