package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.UserDao;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.RegisterUserDto;
import cz.janakdom.backend.model.dto.RenewPasswordDto;
import cz.janakdom.backend.model.dto.UpdateUserDto;
import cz.janakdom.backend.model.output.AuthenticatedUser;
import cz.janakdom.backend.security.AuthLevel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AreaService areaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsernameOrEmail(username, username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public User findByUsernameOrEmail(String username, String email) {
        return userDao.findByUsernameOrEmail(username, email);
    }

    public boolean isEmailUnique(String email) {
        return userDao.findByEmail(email) == null;
    }

    public boolean isUsernameUnique(String username) {
        return userDao.findByUsername(username) == null;
    }

    public boolean delete(int id) {
        User user = findById(id);
        if (user != null) {
            if (user.getResponsibleFor().size() > 0 || user.getIncidents().size() > 0) {
                user.setIsDeleted(true);
                userDao.save(user);
            } else {
                userDao.deleteById(id);
            }
            return true;
        }
        return false;
    }

    public List<User> findAll() {
        return userDao.findAllByIsDeletedIsFalse();
    }

    public User findById(int id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.orElse(null);
    }

    public User update(int id, UpdateUserDto userDto) {
        User user = findById(id);
        if (user != null) {
            BeanUtils.copyProperties(userDto, user, "password", "username");
            user.setArea(areaService.findById(userDto.getAreaId()));
            userDao.save(user);
        }
        return user;
    }

    public boolean renewPassword(int id, RenewPasswordDto renewPasswordDto) {
        User user = findById(id);

        if (user == null || user.getRenewTask() == false) {
           return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(renewPasswordDto.getPassword()));
        user.setRenewTask(false);
        userDao.save(user);
        return true;
    }

    public User save(RegisterUserDto user) {
        User newUser = new User();
        newUser.setRole(roleService.findByRole("ROLE_USER"));
        BeanUtils.copyProperties(user, newUser, "password");
        newUser.setArea(areaService.findById(user.getAreaId()));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(user.getPassword()));

        return userDao.save(newUser);
    }

    public AuthenticatedUser authenticatedUserOutput(User user) {
        AuthenticatedUser authenticated = new AuthenticatedUser();
        BeanUtils.copyProperties(user, authenticated, "id", "password", "isDeleted", "email", "responsibleFor", "role", "area", "incidents");
        authenticated.setRole(user.getRole().getName());
        if (authenticated.getFirstname() == null) {
            authenticated.setFirstname("");
        }
        if (authenticated.getSurname() == null) {
            authenticated.setSurname("");
        }
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            authenticated.setArea(null);
        } else {
            authenticated.setArea(user.getArea());
        }
        return authenticated;
    }

    public void updatePass(User user, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));
        userDao.save(user);
    }

    public boolean checkPermission(AuthLevel authLevel, User user) {
        switch (authLevel) {
            case USER:
                return user.getRole().getName().equals("ROLE_USER");
            case ADMIN:
                return user.getRole().getName().equals("ROLE_ADMIN");
            default:
        }
        return false;
    }

    public void renewTask(User user) {
        user.setRenewTask(true);
        userDao.save(user);
    }
}
