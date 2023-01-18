package dev.mapra.lbms.model.Interfaces;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public final class BookInterface {
    private Long id;
    private String name;
    private String publishYear;
    private Long availableCount;
    private List<String> writers;
    private String publisher;

    public BookInterface(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.publishYear = book.getPublishYear();
        this.availableCount = book.getAvailableCount();
        this.writers = new ArrayList<>(book.getWriters().size());
        for (Writer w:
                book.getWriters()) {
            this.writers.add(w.getLastName());
        }
        this.publisher = book.getPublisher().getName();
    }

    public Book toEntity (List<Writer> writers, Admin admin, Publisher publisher) {
        return new Book(this.id,this.name,this.publishYear,this.availableCount,writers,publisher,admin);
    }
}
