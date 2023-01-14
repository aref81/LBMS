package dev.mapra.lbms.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Represents a Writer -> Writes some books
 * keeps :
 *  - id (auto generated)
 *  - name (first name , last name)
 *  - date of birth
 *  - a list of books written by this writer, mapped to book entity,
 *      considering each writer can write multiple books and each book
 *      can be written by multiple writers
 *
 * @author mohammadhoseinaref
 * @version 0.1
 * @see Book
 */
@Data
@Entity
@Table(name = "Writers")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // auto generated id
    @Column(name = "firstName")
    private String firstName; // writer's first name, not necessary, part of "name" composite attribute
    @Column(name = "lastName",nullable = false)
    private String lastName; // writer's last name, necessary, part of "name" composite attribute
    @Column(name = "birth")
    private LocalDateTime birth; // writer's date of birth, not necessary
    @ManyToMany
    private List<Book> books; // list of books written by this writer, mapped to book entity


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
