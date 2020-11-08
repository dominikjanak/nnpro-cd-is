package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "incident")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime creationDatetime;

    @Column(nullable = false, length = 50)
    private String location;

    @Column(nullable = false, length = 5000)
    private String note;

    @Column(nullable = true, length = 5000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "securityIncident_id", referencedColumnName = "id")
    private SecurityIncident securityIncident = null;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "premiseIncident_id", referencedColumnName = "id")
    private PremiseIncident premiseIncident = null;

    public void setSecurityIncident(SecurityIncident securityIncident) throws Exception {
        if (this.premiseIncident != null)
            throw new Exception("The security incident cannot be set because the premise incident is assigned!");
        this.securityIncident = securityIncident;
    }

    public void setPremiseIncident(PremiseIncident premiseIncident) throws Exception {
        if (this.securityIncident != null)
            throw new Exception("The premise incident cannot be set because the security incident is assigned!");
        this.premiseIncident = premiseIncident;
    }
}
