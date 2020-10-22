package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false, length = 3, unique = true)
    private String abbreviation;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<Incident> incidents = new ArrayList<>();
}
