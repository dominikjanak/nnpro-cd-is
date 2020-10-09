package cz.upce.nnpro.cd.controller;

import cz.upce.nnpro.cd.model.database.User;
import cz.upce.nnpro.cd.model.dto.Credentials;
import cz.upce.nnpro.cd.model.dto.Identity;
import cz.upce.nnpro.cd.service.AuthService;
import cz.upce.nnpro.cd.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/")
    public Identity auth(@RequestBody Credentials credentials) {
        UserDetails user = getAuthenticatedUser(credentials);
        Identity identity = new Identity();
        identity.setName(user.getUsername());
        identity.setToken(jwtUtil.generateToken(user.getUsername()));
        return identity;
    }

    @PostMapping("/new/")
    public Identity register(@RequestBody Credentials credentials) {
        User user = authService.addUser(credentials);
        Identity identity = new Identity();
        identity.setName(user.getUsername());
        identity.setToken(jwtUtil.generateToken(user.getUsername()));

        return identity;
    }

    private UserDetails getAuthenticatedUser(Credentials credentials) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getName(), credentials.getPassword()));
            return authService.loadUserByUsername(credentials.getName());
        } catch (Exception e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }


}
