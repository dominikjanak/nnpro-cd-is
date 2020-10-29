package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* EXTERNAL */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "attacked_subject")
public class AttackedSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 200, unique = true)
    private String name;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "attackedSubjects")
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();
}
