package dev.mapra.lbms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a Book -> entity is published by a publisher and written
 *                      by one or more writer
 * <p>
 * keeps :
 *  - id (auto generated)
 *  - book's name
 *  - the year that book was published
 *  - number of books available right now
 *  - a list of writers, mapped to writer entity
 *      considering a writer can have multiple writers
 *  - the publisher, mapped to publisher entity
 *      considering each book is published by only one
 *      publisher
 *  - admin who added the book
 *
 * @author mohammadhoseinaref
 * @version 0.1
 * @see Publisher
 * @see Writer
 */
@Data
@Entity
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // auto generated id
    @Column(name = "name", nullable = false)
    private String name; // name of the book, necessary
    @Column(name = "publishYear")
    private String publishYear; // the year that book was published
    @Column(name = "availableCount")
    private Long availableCount; // number of books available right now
    @ManyToMany
    List<Writer> writers; // a list of book's writers, mapped to writer entity
    @OneToOne
    Publisher publisher; // the publisher of book, mapped to publisher entity
    @ManyToOne
    Admin admin; // the admin who added this book
}
