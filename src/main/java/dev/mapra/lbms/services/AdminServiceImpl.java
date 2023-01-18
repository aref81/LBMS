package dev.mapra.lbms.services;

import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Interfaces.BookInterface;
import dev.mapra.lbms.model.Interfaces.PublisherInterface;
import dev.mapra.lbms.model.Interfaces.WriterInterface;
import dev.mapra.lbms.model.Publisher;
import dev.mapra.lbms.model.Writer;
import dev.mapra.lbms.repository.AdminRepository;
import dev.mapra.lbms.repository.BookRepository;
import dev.mapra.lbms.repository.PublisherRepository;
import dev.mapra.lbms.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
 public class AdminServiceImpl implements AdminService, UserDetailsService {
    private final AdminRepository adminRepository;
    private final PublisherRepository publisherRepository;
    private final WriterRepository writerRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("Admin not found in database"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN"); // our only role is ADMIN
        authorities.add(authority);

        return new User(admin.getUserName(),admin.getPassword(),authorities);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public Admin getAdmin(String userName) {
        return adminRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("username is not in the database"));
    }

    @Override
    public PublisherInterface savePublisher(PublisherInterface publisherInterface, String userName) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("username is not in the database"));
        Publisher initPublisher = publisherToEntity(admin,publisherInterface);
        Publisher savedPublisher = publisherRepository.save(initPublisher);
        admin.getPublishers().add(savedPublisher);
        adminRepository.save(admin);
        return new PublisherInterface(savedPublisher);
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        Publisher savedPublisher = publisherRepository.save(publisher);
        Admin admin = publisher.getAdmin();
        admin.getPublishers().add(savedPublisher);
        adminRepository.save(admin);
        return savedPublisher;
    }

    @Override
    public Publisher getPublisher(String name) {
        return publisherRepository.findByName(name).orElseThrow(() -> new RuntimeException("publisher not found"));
    }

    @Override
    public WriterInterface saveWriter(WriterInterface writerInterface, String userName) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("username is not in the database"));
        Writer initWriter = writerToEntity(admin,writerInterface);
        Writer savedWriter = writerRepository.save(initWriter);
        admin.getWriters().add(savedWriter);
        adminRepository.save(admin);
        return new WriterInterface(savedWriter);
    }

    @Override
    public Writer saveWriter(Writer writer) {
        Writer savedWriter = writerRepository.save(writer);
        Admin admin = writer.getAdmin();
        admin.getWriters().add(savedWriter);
        adminRepository.save(admin);
        return savedWriter;
    }

    @Override
    public Writer getWriter(String lastName) {
        return writerRepository.findByLastName(lastName).orElseThrow(() -> new RuntimeException("writer not found"));
    }

    @Override
    public BookInterface saveBook(BookInterface bookInterface, String userName) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("username is not in the database"));
        Book initBook = bookToEntity(admin,bookInterface);
        Book savedBook = bookRepository.save(initBook);
        for (Writer w:
             savedBook.getWriters()) {
            w.getBooks().add(savedBook);
            writerRepository.save(w);
        }

        savedBook.getPublisher().getBooks().add(savedBook);
        publisherRepository.save(savedBook.getPublisher());

        admin.getBooks().add(savedBook);
        adminRepository.save(admin);

        return new BookInterface(savedBook);
    }

    @Override
    public Book saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        Admin admin = book.getAdmin();

        for (Writer w:
                savedBook.getWriters()) {
            w.getBooks().add(savedBook);
            writerRepository.save(w);
        }

        savedBook.getPublisher().getBooks().add(savedBook);
        publisherRepository.save(savedBook.getPublisher());

        admin.getBooks().add(savedBook);
        adminRepository.save(admin);

        return savedBook;
    }

    @Override
    public List<Book> getBooksList(String userName) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        return bookRepository.findAllByAdmin(admin);
    }

    private Publisher publisherToEntity (Admin admin,PublisherInterface publisherInterface) {
        List<Book> books = bookRepository.findAllByAdmin(admin);
        return publisherInterface.toEntity(books,admin);
    }

    private Book bookToEntity (Admin admin,BookInterface bookInterface) {
        if (bookInterface.getWriters() == null) {
            throw new RuntimeException("writer list is missing");
        }

        List<Writer> writers = new ArrayList<>(bookInterface.getWriters().size());

        for (String w:
             bookInterface.getWriters()) {
            writers.add(writerRepository.findByLastName(w).orElseThrow(() -> new RuntimeException("writer not found")));
        }

        Publisher publisher = publisherRepository.findByName(bookInterface.getPublisher()).orElseThrow(() -> new RuntimeException("Publisher not found"));

        return bookInterface.toEntity(writers,admin,publisher);
    }

    private Writer writerToEntity (Admin admin,WriterInterface writerInterface) {
        List<Book> books = new ArrayList<>();
        if (writerInterface.getBooks() != null) {

            for (String b :
                    writerInterface.getBooks()) {
                books.add(bookRepository.findByName(b).orElseThrow(() -> new RuntimeException("book not found")));
            }
        }

        return writerInterface.toEntity(books,admin);
    }
}
