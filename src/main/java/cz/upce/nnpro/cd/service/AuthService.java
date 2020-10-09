package cz.upce.nnpro.cd.service;

import cz.upce.nnpro.cd.dao.UserRepository;
import cz.upce.nnpro.cd.model.database.User;
import cz.upce.nnpro.cd.model.dto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = findByUsername(name);

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User addUser(Credentials credentials) {
        User user = new User();
        user.setUsername(credentials.getName());
        user.setPassword(encoder.encode(credentials.getPassword()));
        return userRepository.save(user);
    }

}
