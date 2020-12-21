package com.springTask.All_E_Shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="fullName")
    private String fullName;

    @Column(name="user_avatar")
    private String userAvatar;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;



    public boolean containsRole(Role role){
        return roles.contains(role);
    }
}
