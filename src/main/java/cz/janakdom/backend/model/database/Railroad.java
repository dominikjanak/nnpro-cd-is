package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* EXTERNAL */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "railroad")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"number", "name"})
})
public class Railroad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String number;

    @Column(nullable = false, length = 500)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "railroad")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();
}
