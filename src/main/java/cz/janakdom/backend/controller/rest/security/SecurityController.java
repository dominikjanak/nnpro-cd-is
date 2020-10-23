package cz.janakdom.backend.controller.rest.security;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.AuthRequest;
import cz.janakdom.backend.model.AuthToken;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.RegisterUserDto;
import cz.janakdom.backend.model.dto.RenewPasswordUserDto;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class SecurityController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @PostMapping("/login")
    public ApiResponse<AuthToken> authenticate(@RequestBody AuthRequest authRequest) throws AuthenticationException {

        User user = doAuthenticate(authRequest.getUsername(), authRequest.getPassword());

        if (user != null) {
            final String token = jwtUtil.generateToken(user.getUsername());
            return new ApiResponse<>(200, "SUCCESS", new AuthToken(user.getUsername(), token));
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID-CREDENTIALS", null);
    }

    @PostMapping("/renew")
    public ApiResponse<Void> authenticate(@RequestBody RenewPasswordUserDto renew) {
        User user = userService.findByUsername(renew.getUsername());

        if (user != null) {
            user.setRenewTask(true);
            userService.update(user);
            return new ApiResponse<Void>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws AuthenticationException {
        return new ApiResponse<>(200, "success", null);
    }

    private User doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findByUsername(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }

    @PostMapping("/register")
    public ApiResponse<AuthToken> register(@RequestBody RegisterUserDto user) {

        if (user.getUsername().length() <= 3
                || user.getSurname().isEmpty()
                || user.getFirstname().isEmpty()
                || user.getPassword().length() <= 6
                || !isValidEmail(user.getEmail())
        ) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID", null);
        }

        if (!userService.isEmailUnique(user.getEmail())) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "EMAIL-ALREADY-USED", null);
        }

        User findUser = userService.findByUsername(user.getUsername());

        if (findUser == null) {
            User persisted = userService.save(user);
            final String token = jwtUtil.generateToken(persisted.getUsername());
            return new ApiResponse<>(200, "SUCCESS", new AuthToken(persisted.getUsername(), token));
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS", null);
    }
}