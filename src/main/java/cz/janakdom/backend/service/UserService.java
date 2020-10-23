package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.RoleDao;
import cz.janakdom.backend.dao.UserDao;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.RegisterUserDto;
import cz.janakdom.backend.model.dto.UpdateUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean isEmailUnique(String email) {
        return userDao.findByEmail(email) == null;
    }

    public void delete(int id) {
        userDao.deleteById(id);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findById(int id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.orElse(null);
    }

    public User update(UpdateUserDto userDto) {
        User user = findById(userDto.getId());
        if (user != null) {
            BeanUtils.copyProperties(userDto, user, "password", "username");
            userDao.save(user);
        }
        return user;
    }

    public User update(User user) {
        User u = findById(user.getId());
        if (u != null) {
            BeanUtils.copyProperties(user, u, "password", "username");
            userDao.save(user);
        }
        return u;
    }

    public User save(RegisterUserDto user) {
        User newUser = new User();
        newUser.setRole(roleService.findByRole("ROLE_USER"));
        BeanUtils.copyProperties(user, newUser, "password");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(user.getPassword()));

        return userDao.save(newUser);
    }
}
