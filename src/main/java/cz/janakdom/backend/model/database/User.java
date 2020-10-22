package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "user")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 35, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(length = 50)
    private String firstname;

    @Getter
    @Setter
    @Column(length = 50)
    private String surname;

    @Getter
    @Setter
    @Column(nullable = false, length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, length = 100, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "area_id", nullable = true)
    private Area area;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<SecurityIncident> responsibleFor = new ArrayList<>();
}
