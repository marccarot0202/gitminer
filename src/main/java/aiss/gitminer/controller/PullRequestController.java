package aiss.gitminer.controller;

import aiss.gitminer.exceptions.IssueNotFoundException;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.PullRequest;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/pullrequest")
public class PullRequestController {

    @Autowired
    PullRequestRepository repo;

    public PullRequestController(PullRequestRepository repo) {
        this.repo = repo;
    }

    //Get all pulls OR Get pulls by author ID OR Get pulls by state
    @GetMapping
    public List<PullRequest> find(
            @RequestParam(required = false) String authorId,
            @RequestParam(required = false) String state) throws IssueNotFoundException {

        List<PullRequest> issues;

        if (authorId != null) {
            issues = repo.findByAuthorId(authorId);
        } else if (state != null) {
            issues = repo.findByState(state);
        } else {
            issues = repo.findAll();
        }

        if (issues.isEmpty()) {
            throw new IssueNotFoundException();
        }

        return issues;
    }

    //Get issue by id
    @GetMapping("/{id}")
    public PullRequest findById(@PathVariable String id) throws IssueNotFoundException {
        Optional<PullRequest> pull = repo.findById(id);
        if (!pull.isPresent()) {
            throw new IssueNotFoundException();
        }
        return pull.get();
    }
}
