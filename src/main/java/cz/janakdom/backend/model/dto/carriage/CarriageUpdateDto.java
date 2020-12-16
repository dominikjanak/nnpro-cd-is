package cz.janakdom.backend.model.dto.carriage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageUpdateDto {
    private String producer;
    private String color;
    private String homeStation;
    private float length;
    private float weight;
}
