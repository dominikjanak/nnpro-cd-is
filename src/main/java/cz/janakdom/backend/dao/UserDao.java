package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
    User findAllByRoleIsNotNullAndRoleName(String role_name );
    List<User> findAllByIsDeletedIsFalse();
}
