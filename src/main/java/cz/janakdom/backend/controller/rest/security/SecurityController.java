package cz.janakdom.backend.controller.rest.security;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.AuthRequest;
import cz.janakdom.backend.model.AuthToken;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.ChangePasswordDto;
import cz.janakdom.backend.model.dto.RegisterUserDto;
import cz.janakdom.backend.model.dto.RenewPasswordUserDto;
import cz.janakdom.backend.model.output.AuthenticatedUser;
import cz.janakdom.backend.security.AuthLevel;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.EmailService;
import cz.janakdom.backend.service.SecurityContext;
import cz.janakdom.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserService userService;
    @Autowired
    private SecurityContext securityContext;

    @PostMapping("/login")
    public ApiResponse<AuthToken> authenticate(@RequestBody AuthRequest authRequest) throws AuthenticationException {

        User user = securityContext.doAuthenticate(authRequest.getUsername(), authRequest.getPassword());
        if (user != null) {
            final String token = jwtUtil.generateToken(user);
            AuthToken tokenWithPayload = new AuthToken(user.getUsername(), user.getFirstname(), user.getSurname(), token);
            return new ApiResponse<>(200, "SUCCESS", tokenWithPayload);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID-CREDENTIALS", null);
    }

    @PostMapping("/renew")
    public ApiResponse<Void> authenticate(@RequestBody RenewPasswordUserDto renew) {
        User user = userService.findByUsernameOrEmail(renew.getUsername(), renew.getUsername());

        if (user != null) {
            userService.renewTask(user);
            return new ApiResponse<Void>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }

    @PostMapping("/me")
    public ApiResponse<AuthenticatedUser> me() {
        User user = securityContext.getAuthenticatedUser();

        if (user != null) {
            AuthenticatedUser authenticated = userService.authenticatedUserOutput(user);
            return new ApiResponse<>(200, "SUCCESS", authenticated);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws AuthenticationException {
        return new ApiResponse<>(200, "success", null);
    }

    @PostMapping("/register")
    public ApiResponse<AuthToken> register(@RequestBody RegisterUserDto user) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        if (user.getUsername().length() <= 3
                || user.getSurname().isEmpty()
                || user.getFirstname().isEmpty()
                || user.getPassword().length() <= 6
                || !EmailService.isValidEmail(user.getEmail())
        ) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID-INPUT-DATA", null);
        }

        if (!userService.isEmailUnique(user.getEmail())) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMAIL-ALREADY-USED", null);
        }

        User findUser = userService.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (findUser == null) {
            User persisted = userService.save(user);
            final String token = jwtUtil.generateToken(persisted);
            AuthToken tokenWithPayload = new AuthToken(persisted.getUsername(), persisted.getFirstname(), persisted.getSurname(), token);
            return new ApiResponse<>(200, "SUCCESS", tokenWithPayload);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
    }

    @PostMapping("/change-password")
    public ApiResponse<Void> processPasswordChange(@RequestBody ChangePasswordDto changePassword) {
        User user = securityContext.getAuthenticatedUser();
        User passwordUser = securityContext.doAuthenticate(user.getUsername(), changePassword.getOldPassword());

        if (passwordUser == null) {
            return new ApiResponse<Void>(HttpStatus.FORBIDDEN.value(), "FORBIDDEN", null);
        }

        if (changePassword.getNewPassword().length() <= 6) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "TOO-SHORT-PASSWORD", null);
        }

        userService.updatePass(passwordUser, changePassword.getNewPassword());
        return new ApiResponse<Void>(HttpStatus.OK.value(), "SUCCESS", null);
    }
}
