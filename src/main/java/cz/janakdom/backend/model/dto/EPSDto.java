package cz.janakdom.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPSDto {
    private String type;
    private String location;
    private Integer building_id;
}
