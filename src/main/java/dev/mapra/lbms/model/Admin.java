package dev.mapra.lbms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
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
}
