package cz.janakdom.backend.model.dto.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingUpdateDto {
    private String owner;
    private String buildingManager;
    private String GasShutOff;
    private String WaterShutOff;
    private String ElectSwitchboard;
}
