package aiss.gitminer.controller;

import aiss.gitminer.exceptions.CommitNotFoundException;
import aiss.gitminer.exceptions.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository repo;

    public IssueController(IssueRepository repo) {
        this.repo = repo;
    }

    //Get all issues OR Get issues by author ID OR Get issues by state
    @GetMapping
    public List<Issue> find(
            @RequestParam(required = false) String authorId,
            @RequestParam(required = false) String state) throws IssueNotFoundException {

        List<Issue> issues;

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
    public Issue findById(@PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = repo.findById(id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    //Get issue's comments
    @GetMapping("/{id}/comments")
    public List<Comment> getIssueComments(@PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = repo.findById(id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue.get().getComments();
    }
}
