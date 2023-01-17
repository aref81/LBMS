package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;
import dev.mapra.lbms.repository.AdminRepository;
import dev.mapra.lbms.repository.BookRepository;
import dev.mapra.lbms.repository.PublisherRepository;
import dev.mapra.lbms.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private final PublisherRepository publisherRepository;
    private final WriterRepository writerRepository;
    private final BookRepository bookRepository;

    @Override
    public String login(String userName, String password) {
        return null;
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Writer saveWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooksList(String userName) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        return admin.getBooks();
    }
}
