package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.RoleDao;
import cz.janakdom.backend.dao.UserDao;
import cz.janakdom.backend.model.database.Role;
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

@Service(value = "roleService")
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findById(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        return optionalRole.orElse(null);
    }

    public Role findByRole(String role) {
        Optional<Role> optionalRole = roleDao.findByName(role);
        return optionalRole.orElse(null);
    }

    public void purge(){
        roleDao.deleteAll();
    }

    public Role save(Role role) {
        return roleDao.save(role);
    }
}
