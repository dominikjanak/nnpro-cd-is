package cz.janakdom.backend.service;

import cz.janakdom.backend.model.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service(value = "securityAuthenticated")
public class SecurityContext {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principals = auth.getPrincipal();

        if (principals instanceof UserDetails) {
            String username = ((UserDetails) principals).getUsername();
            return userService.findByUsernameOrEmail(username, username);
        }
        return null;
    }

    public User doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findByUsernameOrEmail(username, username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }
}
