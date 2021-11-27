package com.joydeep.poc.services;

import com.joydeep.poc.models.Book;
import com.joydeep.poc.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class BookService implements EntityService<Book> {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String getEntityPageById(String bookId, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-found";
    }
}
