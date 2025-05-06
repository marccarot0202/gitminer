package aiss.gitminer.controller;

import aiss.gitminer.exceptions.CommentNotFoundException;
import aiss.gitminer.exceptions.CommitNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    CommitRepository repo;

    public CommitController(CommitRepository repo) {
        this.repo = repo;
    }

    //Get all commits OR Get commits by email address
    @GetMapping
    public List<Commit> find(@RequestParam(value = "email", required = false) String email) throws CommitNotFoundException {
        if (email != null) {
            Optional<Commit> commit = repo.findByAuthorEmail(email);
            if (commit.isEmpty()) {
                throw new CommitNotFoundException();
            }
            return List.of(commit.get());
        } else {
            return repo.findAll();
        }
    }

    //Get commit
    @GetMapping("/{id}")
    public Commit findById(@PathVariable String id) throws CommitNotFoundException {
        Optional<Commit> commit = repo.findById(id);
        if (!commit.isPresent()) {
            throw new CommitNotFoundException();
        }
        return commit.get();
    }
}
