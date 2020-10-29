package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* EXTERNAL */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "intervention_type")
public class InterventionType {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500, unique = true)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interventionType")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<FireIncident> fireIncidents = new ArrayList<>();
}
