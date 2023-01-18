package dev.mapra.lbms.model.Interfaces;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public final class WriterInterface {
    private Long id;
    private String firstName;
    private String lastName;
    private String birth;
    private List<String> books;

    public WriterInterface(Writer writer) {
        this.id = writer.getId();
        this.firstName = writer.getFirstName();
        this.lastName = writer.getLastName();
        this.birth = writer.getBirth();
        this.books = new ArrayList<>(writer.getBooks().size());
        for (Book b:
                writer.getBooks()) {
            books.add(b.getName());
        }
    }

    public Writer toEntity (List<Book> books, Admin admin) {
        return new Writer(this.id,this.firstName,this.lastName,this.birth,books,admin);
    }
}
