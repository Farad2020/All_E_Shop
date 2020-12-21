package com.springTask.All_E_Shop.repositories;

import com.springTask.All_E_Shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail( String email );
    List<User> findAll();
    void deleteById(Long id);
}
