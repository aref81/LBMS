package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;

import java.util.List;

public interface AdminService {
    public String login (String userName,String password);

    public Publisher savePublisher (Publisher publisher);

    public Writer saveWriter (Writer writer);

    public Book saveBook (Book book);

    List<Book> getBooksList(String userName);
}
