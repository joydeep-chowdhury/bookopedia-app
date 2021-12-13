package com.joydeep.poc.services;

import com.joydeep.poc.models.Book;
import com.joydeep.poc.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

import static com.joydeep.poc.utils.BookUtils.BOOK_COVER_TEMPLATE_URL;

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
            String bookCoverFinalUrl = "/images/no-image.jpg";
            if(book.getCoverIds()!=null && !book.getCoverIds().isEmpty()){
                bookCoverFinalUrl = String.format(BOOK_COVER_TEMPLATE_URL,book.getCoverIds().get(0));
            }
            model.addAttribute("coverImageUrl",bookCoverFinalUrl);
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-found";
    }
}
