package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {
    Optional<Commit> findByAuthorEmail(String author_email);
}
