package cz.janakdom.backend.controller.rest.internal;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.RegisterUserDto;
import cz.janakdom.backend.model.dto.UpdateUserDto;
import cz.janakdom.backend.security.AuthLevel;
import cz.janakdom.backend.service.EmailService;
import cz.janakdom.backend.service.SecurityContext;
import cz.janakdom.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityContext securityContext;

    @GetMapping("/")
    public ApiResponse<List<User>> listUsers() {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", userService.findAll());
    }

    @PostMapping("/")
    public ApiResponse<Void> createUser(RegisterUserDto registerUser) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        if (registerUser.getUsername().length() <= 3
                || registerUser.getSurname().isEmpty()
                || registerUser.getFirstname().isEmpty()
                || registerUser.getPassword().length() <= 6
                || !EmailService.isValidEmail(registerUser.getEmail())
        ) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID-INPUT-DATA", null);
        }

        if (!userService.isEmailUnique(registerUser.getEmail())) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMAIL-ALREADY-USED", null);
        }

        if (!userService.isUsernameUnique(registerUser.getUsername())) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "USERNAME-ALREADY-USED", null);
        }

        User findUser = userService.findByUsernameOrEmail(registerUser.getUsername(), registerUser.getEmail());

        if (findUser == null) {
            User persisted = userService.save(registerUser);
            return new ApiResponse<>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "ALREADY-EXISTS", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<User> findUser(@PathVariable int id) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        User user = userService.findById(id);
        if (user != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", user);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }


    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable int id, @RequestBody UpdateUserDto updateUserDto) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        if (updateUserDto.getEmail() == null || updateUserDto.getEmail().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-EMAIL", null);
        }
        if (updateUserDto.getFirstname() == null || updateUserDto.getFirstname().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-FIRSTNAME", null);
        }
        if (updateUserDto.getSurname() == null || updateUserDto.getSurname().isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "EMPTY-SURNAME", null);
        }

        User updatedUser = userService.update(id, updateUserDto);
        if (updatedUser != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", updatedUser);
        }
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable int id) {
        User authenticatedUser = securityContext.getAuthenticatedUser();
        if (!userService.checkPermission(AuthLevel.ADMIN, authenticatedUser)) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "INVALID-AUTHORIZATION", null);
        }

        if (userService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD-REQUEST", null);
    }
}
