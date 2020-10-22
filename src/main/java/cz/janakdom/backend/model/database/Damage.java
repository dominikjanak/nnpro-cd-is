package cz.janakdom.backend.model.database;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false)
    private float financeValue;

    @Getter
    @Setter
    @Column(nullable = false, length = 100)
    private String attackedObject;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "security_incident_id", nullable = false)
    private SecurityIncident securityIncident;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "damage_type_id", nullable = false)
    private DamageType damageType;
}
