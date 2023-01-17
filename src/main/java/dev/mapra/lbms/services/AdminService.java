package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;

import java.util.List;

public interface AdminService {

    public Admin getAdmin (String userName);

    public Publisher savePublisher (Publisher publisher);

    public Writer saveWriter (Writer writer);

    public Book saveBook (Book book);

    List<Book> getBooksList();
}
