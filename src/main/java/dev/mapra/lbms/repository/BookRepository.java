package dev.mapra.lbms.repository;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAdmin(Admin admin);
}
