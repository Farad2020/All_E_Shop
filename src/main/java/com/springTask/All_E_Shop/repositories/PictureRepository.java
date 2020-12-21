package com.springTask.All_E_Shop.repositories;

import com.springTask.All_E_Shop.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAll();
    void deleteById(Long id);
}
