package cz.janakdom.backend.model.dto.carriage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageDto {
    private String serialNumber;
    private String producer;
    private String color;
    private String homeStation;
    private float length;
    private float weight;
    private String depo;
}
