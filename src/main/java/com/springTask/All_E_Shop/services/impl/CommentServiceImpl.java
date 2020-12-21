package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.Comment;
import com.springTask.All_E_Shop.entities.ShopItem;
import com.springTask.All_E_Shop.entities.User;
import com.springTask.All_E_Shop.repositories.CommentRepository;
import com.springTask.All_E_Shop.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getAllCommentsByItem(ShopItem shopItem) {
        return commentRepository.findAllByShopItem(shopItem);
    }

    @Override
    public List<Comment> getAllCommentsByAuthor(User user) {
        return commentRepository.findAllByAuthor(user);
    }

    @Override
    public Comment getComment(Long id) {
        if (commentRepository.existsById(id))
            return commentRepository.getOne(id);
        return null;
    }

    @Override
    public void deleteComment(Long id) {
        if (commentRepository.existsById(id))
            commentRepository.deleteById(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
