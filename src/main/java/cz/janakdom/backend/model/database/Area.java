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
@Entity(name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Getter
    @Setter
    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<User> users = new ArrayList<>();


    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<Region> regions = new ArrayList<>();
}
