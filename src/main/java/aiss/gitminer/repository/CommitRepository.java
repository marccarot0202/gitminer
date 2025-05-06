package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommitRepository extends JpaRepository<Commit, String> {
    Optional<Commit> findByAuthorEmail(String email);
}
