package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* EXTERNAL */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "intervention_type")
public class InterventionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 500, unique = true)
    private String name;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interventionType")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<FireIncident> fireIncidents = new ArrayList<>();
}
