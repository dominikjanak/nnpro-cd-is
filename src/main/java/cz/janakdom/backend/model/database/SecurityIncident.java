package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "security_incident")
public class SecurityIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean checked = false;

    @Column(nullable = false)
    private Boolean crime = false;

    @Column(nullable = false)
    private Boolean police = false;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "manager", nullable = false)
    private User manager;

    @OneToOne(mappedBy = "securityIncident")
    private Incident incident;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "carriage", nullable = false)
    private Carriage carriage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fireIncident_id", referencedColumnName = "id")
    private final FireIncident fireIncident = null;

    @ManyToMany
    @JoinTable(
            name = "fire_brigade_unit_incident",
            joinColumns = @JoinColumn(name = "security_incident_id"),
            inverseJoinColumns = @JoinColumn(name = "fire_brigade_unit_id"))
    private final List<FireBrigadeUnit> fireBrigadeUnits = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "attacked_subjects_incident",
            joinColumns = @JoinColumn(name = "security_incident_id"),
            inverseJoinColumns = @JoinColumn(name = "attacked_subject_id"))
    private final List<AttackedSubject> attackedSubjects = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "railroad_id", nullable = false)
    private Railroad railroad;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityIncident")
    private final List<Damage> damages = new ArrayList<>();
}
