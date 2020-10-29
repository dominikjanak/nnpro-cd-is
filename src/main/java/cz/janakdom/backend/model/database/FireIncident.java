package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "fire_incident")
public class FireIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Date validFrom;

    @Getter
    @Setter
    @Column(nullable = false)
    private Date validTo;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @OneToOne(mappedBy = "fireIncident")
    private SecurityIncident securityIncident;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "intervention_type_id", nullable = false)
    private InterventionType interventionType;

    public Boolean setValidRange(Date from, Date to) {
        if (from.before(to)) {
            this.validFrom = from;
            this.validTo = to;
            return true;
        }
        return false;
    }
}
