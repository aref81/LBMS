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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
 public class AdminServiceImpl implements AdminService, UserDetailsService {
    private AdminRepository adminRepository;
    private final PublisherRepository publisherRepository;
    private final WriterRepository writerRepository;
    private final BookRepository bookRepository;

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
    public Admin getAdmin(String userName) {
        return adminRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("username is not in the database"));
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
    public List<Book> getBooksList() {
//        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        return null;
    }
}
