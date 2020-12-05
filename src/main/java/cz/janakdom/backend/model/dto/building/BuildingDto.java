package cz.janakdom.backend.model.dto.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {
    private String innerno;
    private String address;
    private String gps;
    private String owner;
    private String buildingManager;
    private String GasShutOff;
    private String WaterShutOff;
    private String ElectSwitchboard;
}
