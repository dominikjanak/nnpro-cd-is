package cz.janakdom.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private String username;
    private String role;
    private String Firstname;
    private String Surname;
    private String token;
}
