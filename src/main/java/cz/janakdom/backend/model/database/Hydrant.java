package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "hydrant")
public class Hydrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Float flowRate;

    @Column(nullable = false, length = 30)
    private String location;

    @Column(nullable = false)
    private LocalDateTime lastCheckDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Building building;
}
