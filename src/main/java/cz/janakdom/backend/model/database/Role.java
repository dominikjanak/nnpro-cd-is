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
@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<User> users = new ArrayList<>();
}
