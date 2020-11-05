package cz.janakdom.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.janakdom.backend.model.database.PremiseIncident;
import cz.janakdom.backend.model.database.Region;
import cz.janakdom.backend.model.database.SecurityIncident;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremiseIncidentDto {
    // Incident
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Prague")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDatetime;
    private String location;
    private String note;
    private String comment;
    private Integer region_id;

    // Premise incident
    private LocalDateTime valid = null;
}