package cz.janakdom.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageDto {
    private String serialNumber;
    private String producer;
    private String color;
    private String homeStation;
}
