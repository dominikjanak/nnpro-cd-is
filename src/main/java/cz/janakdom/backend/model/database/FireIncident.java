package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validTo;

    @OneToOne(mappedBy = "fireIncident")
    private SecurityIncident securityIncident;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "intervention_type_id", nullable = false)
    private InterventionType interventionType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "fireIncident")
    private final List<Damage> damages = new ArrayList<>();

    public Boolean setValidRange(LocalDateTime from, LocalDateTime to) {
        if (from.isBefore(to)) {
            this.validFrom = from;
            this.validTo = to;
            return true;
        }
        return false;
    }
}
