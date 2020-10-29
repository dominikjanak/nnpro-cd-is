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
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35, unique = true)
    private String username;

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String surname;

    @Column(nullable = false, length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private Boolean renewTask = false;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false, length = 100, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<SecurityIncident> responsibleFor = new ArrayList<>();
}
