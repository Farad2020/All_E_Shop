package com.springTask.All_E_Shop.repositories;

import com.springTask.All_E_Shop.entities.Comment;
import com.springTask.All_E_Shop.entities.ShopItem;
import com.springTask.All_E_Shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthor(User user);
    List<Comment> findAllByShopItem(ShopItem shopItem);

}
