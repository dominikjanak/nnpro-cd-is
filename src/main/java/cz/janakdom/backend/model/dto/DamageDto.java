package cz.janakdom.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageDto {
    private float financeValue;
    private String attackedObject;
    private Integer damageTypeId;
    private Integer securityIncidentId = null;
    private Integer fireIncidentId = null;
}
