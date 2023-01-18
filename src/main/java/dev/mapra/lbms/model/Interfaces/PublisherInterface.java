package dev.mapra.lbms.model.Interfaces;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * implements the view of info that user has access to :
 * - id
 * - name
 * - the year of foundation
 * - list of books name
 *
 * @author mohammadhoseinaref
 * @version 1.0
 * @see Publisher
 */
@Data
@AllArgsConstructor
public final class PublisherInterface {
    private Long id;
    private String name;
    private String foundYear;
    private List<String> books;

    public PublisherInterface(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.foundYear = publisher.getFoundYear();
        this.books = new ArrayList<>(publisher.getBooks().size());
        for (Book b:
                publisher.getBooks()) {
            this.books.add(b.getName());
        }
    }

    public Publisher toEntity(List<Book> books,Admin admin) {
        return new Publisher(this.id,this.name,this.foundYear,books,admin);
    }
}
