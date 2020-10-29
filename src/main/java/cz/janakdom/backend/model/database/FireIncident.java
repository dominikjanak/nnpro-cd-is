package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "fire_incident")
public class FireIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date validFrom;

    @Column(nullable = false)
    private Date validTo;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

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
