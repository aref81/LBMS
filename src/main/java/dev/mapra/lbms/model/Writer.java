package dev.mapra.lbms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Represents a Writer -> Writes some books
 * <p>
 * keeps :
 *  - id (auto generated)
 *  - name (first name , last name)
 *  - date of birth
 *  - a list of books written by this writer, mapped to book entity,
 *      considering each writer can write multiple books and each book
 *      can be written by multiple writers
 *  - admin who added the writer
 * </>
 * @author mohammadhoseinaref
 * @version 1.0
 * @see Book
 */
@Data
@Entity
@Table(name = "Writers")
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // auto generated id
    @Column(name = "firstName")
    private String firstName; // writer's first name, not necessary, part of "name" composite attribute
    @Column(name = "lastName",nullable = false,unique = true)
    private String lastName; // writer's last name, necessary, part of "name" composite attribute
    @Column(name = "birth")
    private String birth; // writer's date of birth, not necessary
    @ManyToMany
    private List<Book> books; // list of books written by this writer, mapped to book entity
    @ManyToOne
    Admin admin; // the admin who added this writer
}
