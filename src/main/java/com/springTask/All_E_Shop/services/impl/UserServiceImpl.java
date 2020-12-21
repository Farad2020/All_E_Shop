package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.Brand;
import com.springTask.All_E_Shop.entities.Role;
import com.springTask.All_E_Shop.entities.User;
import com.springTask.All_E_Shop.repositories.RoleRepository;
import com.springTask.All_E_Shop.repositories.UserRepository;
import com.springTask.All_E_Shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User myUser = userRepository.findByEmail(s);

        if(myUser != null){

            org.springframework.security.core.userdetails.User secUser
                    = new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());

            return secUser;
        }

        throw new UsernameNotFoundException("Username Not Found");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if( checkUser == null ){
            Role role = roleRepository.findByName("ROLE_USER");
            if( role != null ){
                ArrayList<Role> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword( passwordEncoder.encode(user.getPassword()) );

                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if(userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        if(userRepository.existsById(id))
            return userRepository.getOne(id);
        else
            return null;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(Long id) {
        if (roleRepository.existsById(id))
            return roleRepository.getOne(id);
        else
            return null;
    }

    @Override
    public boolean checkEncryptedPassword(User user, String old_password) {
        return passwordEncoder.matches(old_password, user.getPassword());
    }

    @Override
    public void setUserPassword(User user, String new_password) {
        user.setPassword( passwordEncoder.encode(new_password));
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        if (roleRepository.existsById(id))
            roleRepository.deleteById(id);
    }
}
