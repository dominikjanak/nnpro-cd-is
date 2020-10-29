package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private float financeValue;

    @Column(nullable = false, length = 100)
    private String attackedObject;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "security_incident_id", nullable = false)
    private SecurityIncident securityIncident;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "damage_type_id", nullable = false)
    private DamageType damageType;
}
