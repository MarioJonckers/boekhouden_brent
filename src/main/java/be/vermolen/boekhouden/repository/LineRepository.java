package be.vermolen.boekhouden.repository;

import be.vermolen.boekhouden.model.line.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
}
