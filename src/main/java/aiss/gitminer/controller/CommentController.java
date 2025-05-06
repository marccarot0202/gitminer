package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;

import aiss.gitminer.exceptions.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Get all comments
    @GetMapping
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    //Get comments by id
    @GetMapping("/{id}")
    public Comment findById(@PathVariable(value="id") String id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }
}
