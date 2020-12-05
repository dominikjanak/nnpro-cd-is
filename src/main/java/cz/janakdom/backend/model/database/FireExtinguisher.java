package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.janakdom.backend.model.enums.ExtinguisherType;
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
@Entity(name = "fire_extinguisher")
public class FireExtinguisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ExtinguisherType type;

    @Column(nullable = false, length = 30)
    private String location;

    @Column(nullable = false)
    private LocalDateTime lastCheckDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Building building;

}
