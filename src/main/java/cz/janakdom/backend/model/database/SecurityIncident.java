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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @Column(nullable = false)
    private Boolean checked = false;

    @Column(nullable = false)
    private Boolean crime = false;

    @Column(nullable = false)
    private Boolean police = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @OneToOne(mappedBy = "securityIncident")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Incident incident;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)//change to true?
    @JoinColumn(name = "carriage_id", nullable = true)//change to true?
    private Carriage carriage;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "building_id", nullable = true)
    private Building building;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fireIncident_id", referencedColumnName = "id")
    private FireIncident fireIncident = null;

    @ManyToMany
    @JoinTable(
            name = "fire_brigade_unit_incident",
            joinColumns = @JoinColumn(name = "security_incident_id"),
            inverseJoinColumns = @JoinColumn(name = "fire_brigade_unit_id"))
    private List<FireBrigadeUnit> fireBrigadeUnits = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "attacked_subjects_incident",
            joinColumns = @JoinColumn(name = "security_incident_id"),
            inverseJoinColumns = @JoinColumn(name = "attacked_subject_id"))
    private List<AttackedSubject> attackedSubjects = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "railroad_id", nullable = false)
    private Railroad railroad;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityIncident")
    private List<Damage> damages = new ArrayList<>();
}
