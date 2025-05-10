package aiss.gitminer.repository;

import aiss.gitminer.model.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PullRequestRepository extends JpaRepository<PullRequest, String> {
    List<PullRequest> findByState(String state);

    List<PullRequest> findByAuthorId(String authorId);
}
