package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getAllComments();
    List<Comment> getAllCommentsByItem(ShopItem shopItem);
    List<Comment> getAllCommentsByAuthor(User user);
    Comment getComment(Long id);
    void deleteComment(Long id);
    Comment saveComment(Comment comment);
}
