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
@Entity(name = "fire_brigade_unit")
public class FireBrigadeUnit {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "fireBrigadeUnits")
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();
}
