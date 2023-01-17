package dev.mapra.lbms.repository;

import dev.mapra.lbms.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {
}
