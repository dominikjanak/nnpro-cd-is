package cz.janakdom.backend.Testing

import cz.janakdom.backend.controller.rest.security.SecurityController
import cz.janakdom.backend.model.ApiResponse
import cz.janakdom.backend.model.AuthRequest
import cz.janakdom.backend.model.dto.RegisterUserDto
import cz.janakdom.backend.service.UserService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ControllerTest {
    @Autowired
    private SecurityController securityController;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    @Test
    void loginTest(){
        String username = "uzivatel";
        String password = "password";
        String email = "uzivatel@email.com";
        RegisterUserDto user = new RegisterUserDto(username: username, password: password, email: email, firstname: "F", surname: "F");
        userService.save(user);

        AuthRequest authRequest = new AuthRequest(username: username, password: password);

        ApiResponse response = securityController.authenticate(authRequest);

        Assert.assertEquals(response.status, 200);
        Assert.assertEquals(response.status_key, "SUCCESS");
        Assert.assertEquals(response.result.username, username);
    }
}
