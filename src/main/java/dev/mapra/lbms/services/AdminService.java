package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Interfaces.BookInterface;
import dev.mapra.lbms.model.Interfaces.PublisherInterface;
import dev.mapra.lbms.model.Interfaces.WriterInterface;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;

import java.util.List;

public interface AdminService {

    public Admin saveAdmin (Admin admin);
    public Admin getAdmin (String userName);
    public PublisherInterface savePublisher (PublisherInterface publisherInterface, String username);
    public Publisher getPublisher (String name);
    public WriterInterface saveWriter (WriterInterface writerInterface, String userName);
    public Writer getWriter (String lastName);
    public BookInterface saveBook (BookInterface bookInterface, String userName);
    List<Book> getBooksList(String username);
}
