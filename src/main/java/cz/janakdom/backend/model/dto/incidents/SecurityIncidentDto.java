package cz.janakdom.backend.model.dto.incidents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.janakdom.backend.model.database.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityIncidentDto extends IncidentDto {

    private Boolean checked;
    private Boolean crime;
    private Boolean police;
    private int manager_id;
    private int carriage_id;
    private int railroad_id;
    private List<Integer> fireBrigadeUnit_ids = new ArrayList<>();
    private List<Integer> attackedSubject_ids = new ArrayList<>();
}