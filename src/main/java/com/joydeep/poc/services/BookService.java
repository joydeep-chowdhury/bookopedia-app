package com.joydeep.poc.services;

import com.joydeep.poc.models.Book;
import com.joydeep.poc.models.UserBooks;
import com.joydeep.poc.models.UserBooksPrimaryKey;
import com.joydeep.poc.repositories.BookRepository;
import com.joydeep.poc.repositories.UserBooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

import static com.joydeep.poc.utils.BookUtils.BOOK_COVER_TEMPLATE_URL;

@Service
public class BookService implements EntityService<Book> {

    private final BookRepository bookRepository;
    private final UserBooksRepository userBooksRepository;

    public BookService(BookRepository bookRepository, UserBooksRepository userBooksRepository) {
        this.bookRepository = bookRepository;
        this.userBooksRepository = userBooksRepository;
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
            if(model.getAttribute("loginId")!=null){
                UserBooksPrimaryKey userBooksPrimaryKey = new UserBooksPrimaryKey();
                userBooksPrimaryKey.setBookId(bookId);
                userBooksPrimaryKey.setUserId((String) model.getAttribute("loginId"));
                Optional<UserBooks> optionalUserBooks = userBooksRepository.findById(userBooksPrimaryKey);
                if(optionalUserBooks.isPresent()){
                    UserBooks userBooks = optionalUserBooks.get();
                    String readingStatus = userBooks.getReadingStatus();
                    model.addAttribute("userBooks",userBooks);
                }
                else{
                    model.addAttribute("userBooks",new UserBooks());
                }
            }

            return "book";
        }
        return "book-not-found";
    }
}
