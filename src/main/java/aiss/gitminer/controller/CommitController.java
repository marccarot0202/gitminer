package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    private final CommitRepository repo;

    public CommitController(CommitRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Commit> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Commit findById(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }
}
