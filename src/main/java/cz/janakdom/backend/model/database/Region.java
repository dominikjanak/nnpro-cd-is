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
@Entity(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 3, unique = true)
    private String abbreviation;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<Incident> incidents = new ArrayList<>();
}
