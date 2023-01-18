package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;

import java.util.List;

public interface AdminService {

    public Admin saveAdmin (Admin admin);
    public Admin getAdmin (String userName);

    public Publisher savePublisher (Publisher publisher);
    public Publisher getPublisher (String name);
    public Writer saveWriter (Writer writer);
    public Writer getWriter (String lastName);
    public Book saveBook (Book book);
    List<Book> getBooksList(String username);
}
