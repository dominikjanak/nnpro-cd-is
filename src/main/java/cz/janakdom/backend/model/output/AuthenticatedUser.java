package cz.janakdom.backend.model.output;

import cz.janakdom.backend.model.database.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUser {
    private String username;
    private String firstname;
    private String surname;
    private Boolean renewTask;
    private String role;
    private Area area;
}
