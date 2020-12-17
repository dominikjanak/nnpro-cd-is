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
@Entity(name = "carriage")
public class Carriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String serialNumber;

    @Column(nullable = false, length = 75)
    private String producer;

    @Column(length = 25)
    private String color;

    @Column(nullable = false, length = 75)
    private String homeStation;

    @Column()
    private Float length;

    @Column()
    private Float weight;

    @Column()
    private String depo;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carriage")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OrderColumn(name="INDEX") // Because persistentBag.equals(persistentBag) returns false even if both objects are empty.
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();

    public Carriage(int id, String serialNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
    }

}
