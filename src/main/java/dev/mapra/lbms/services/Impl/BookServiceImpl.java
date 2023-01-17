package dev.mapra.lbms.services.Impl;

import dev.mapra.lbms.repository.BookRepository;
import dev.mapra.lbms.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
