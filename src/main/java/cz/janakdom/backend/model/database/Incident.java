package cz.janakdom.backend.model.database;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "incident")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private Date creationDatetime;

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String location;

    @Getter
    @Setter
    @Column(nullable = false, length = 5000)
    private String note;

    @Getter
    @Setter
    @Column(nullable = true, length = 5000)
    private String comment;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "securityIncident_id", referencedColumnName = "id")
    private SecurityIncident securityIncident = null;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "premiseIncident_id", referencedColumnName = "id")
    private PremiseIncident premiseIncident = null;

    public Incident setSecurityIncident(SecurityIncident securityIncident) throws Exception {
        if (this.premiseIncident != null)
            throw new Exception("The security incident cannot be set because the premise incident is assigned!");
        this.securityIncident = securityIncident;
        return this;
    }

    public Incident setpremiseIncident(PremiseIncident premiseIncident) throws Exception {
        if (this.securityIncident != null)
            throw new Exception("The premise incident cannot be set because the security incident is assigned!");
        this.premiseIncident = premiseIncident;
        return this;
    }
}
