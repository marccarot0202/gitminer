package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    private final IssueRepository repo;

    public IssueController(IssueRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Issue> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/status/{state}")
    public List<Issue> findByState(@PathVariable String state) {
        return repo.findByState(state);
    }
}
