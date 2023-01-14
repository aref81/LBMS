package dev.mapra.lbms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a publisher -> Publishes some books
 * keeps :
 *  - id (auto generated)
 *  - name
 *  - Year of foundation
 *  - a list of books published by this publisher, mapped to book entity
 *    considering each publisher can publish multiple books but each book
 *    can only be published by one publisher
 *
 * @author mohammadhoseinaref
 * @version 0.1
 * @see Book
 */
@Data
@Entity
@Table(name = "Publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // auto generated id
    @Column(name = "name", nullable = false)
    private String name; // publisher's name, necessary
    @Column(name = "foundYear")
    private String foundYear; // publisher's year of foundation, not necessary
    @OneToMany
    List<Book> books; // a list of books published by this publisher, mapped to book entity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoundYear() {
        return foundYear;
    }

    public void setFoundYear(String foundYear) {
        this.foundYear = foundYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
