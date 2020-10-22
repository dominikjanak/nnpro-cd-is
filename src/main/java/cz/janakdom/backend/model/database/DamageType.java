package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "damage_type")
public class DamageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 500)
    private String name;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "damageType")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<Damage> damages = new ArrayList<>();
}
