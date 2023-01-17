package dev.mapra.lbms.model;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.List;

/**
 * Represents an Admin in the system (end user interface) -> manages library,
 *                                      adds books/publishers/writers
 *                                      have access to list of its books/publishers/writers
 *                                      users can be authenticated as one with a username and password
 * <p>
 * keeps :
 *  //- id (auto generated)//
 *  - user name (also acts as primary key in the Admins Table)
 *  - password
 *  - a list of added book
 *  - a list of added publishers
 *  - a list of added writers
 * <p>
 *  books - publishers - writers should be consistent with each other
 *  developed with the idea that each admin has a different set of books - publishers - writers
 * @author mohammadhoseinaref
 * @version 0.1
 * @see Book
 * @see Writer
 * @see Publisher
 */

@Data
@Entity
@Table(name = "Admins")
public class Admin {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
    @Id
    @Column(name = "userName",nullable = false,unique = true)
    private String userName;
    @Column(name = "password",nullable = false)
    private String password;
    @OneToMany
    List<Book> books;
    @OneToMany
    List<Publisher> publishers;
    @OneToMany
    List<Writer> writers;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }
}
