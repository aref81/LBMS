package dev.mapra.lbms.repository;

import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Optional<Writer> findByLastName(String lastName);
}
