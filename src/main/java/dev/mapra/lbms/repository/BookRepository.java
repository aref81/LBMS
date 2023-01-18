package dev.mapra.lbms.repository;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAdmin(Admin admin);
    Optional<Book> findByName(String name);
    @Query(value = "UPDATE Book b SET b.availableCount = :num")
    @Modifying
    @Transactional
    int resetAvailableTo(@Param(value = "num") Long num);
}
