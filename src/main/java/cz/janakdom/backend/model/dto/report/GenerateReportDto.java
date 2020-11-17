package cz.janakdom.backend.model.dto.report;

import lombok.Data;

@Data
public class GenerateReportDto {
    private String token;
    private Boolean force = false;
}
