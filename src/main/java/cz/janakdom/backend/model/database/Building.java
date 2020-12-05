package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 12, unique = true)
    private String innerno;

    @Column(nullable = false, length = 30)
    private String gps;

    @Column(nullable = false, length = 50)
    private String owner;

    @Column(nullable = false, length = 50)
    private String buildingManager;

    @Column(nullable = false, length = 50)
    private String GasShutOff;

    @Column(nullable = false, length = 50)
    private String WaterShutOff;

    @Column(nullable = false, length = 50)
    private String ElectSwitchboard;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "building")
    private List<FileDB> files;

    @OneToMany(mappedBy = "building")
    private List<TelNumber> telNumbers;

    @OneToMany(mappedBy = "building")
    private List<TechnicalSystem> technicalSystems;

    @OneToMany(mappedBy = "building")
    private List<FireExtinguisher> fireExtinguishers;

    @OneToMany(mappedBy = "building")
    private List<EPS> epsList;

    @OneToMany( mappedBy = "building")
    private List<Hydrant> hydrants;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OrderColumn(name="INDEX") // Because persistentBag.equals(persistentBag) returns false even if both objects are empty.
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();
}
