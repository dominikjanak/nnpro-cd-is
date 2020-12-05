package cz.janakdom.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSystemDto {
    private String name;
    private String systemType;
    private String location;
    private String manufacturer;
    private Integer building_id;
}
