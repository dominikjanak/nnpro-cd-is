package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "carriage")
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String serialNumber;

    @Getter
    @Setter
    @Column(nullable = false, length = 75)
    private String producer;

    @Getter
    @Setter
    @Column(length = 25)
    private String color;

    @Getter
    @Setter
    @Column(nullable = false, length = 75)
    private String homeStation;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carriage")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<SecurityIncident> securityIncidents = new ArrayList<>();
}
