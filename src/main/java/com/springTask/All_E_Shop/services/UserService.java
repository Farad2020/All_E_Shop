package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.Brand;
import com.springTask.All_E_Shop.entities.Role;
import com.springTask.All_E_Shop.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserByEmail(String email);
    User getUser(Long id);
    User createUser(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    User saveUser(User user);

    boolean checkEncryptedPassword(User user, String old_password);
    void setUserPassword(User user, String new_password);

    List<Role> getRoles();
    Role getRole(Long id);
    Role saveRole(Role role);
    Role createRole(Role role);
    void deleteRole(Long id);


}
