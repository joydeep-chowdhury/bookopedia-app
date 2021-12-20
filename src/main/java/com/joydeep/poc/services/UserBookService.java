package com.joydeep.poc.services;

import com.joydeep.poc.models.UserBooks;
import com.joydeep.poc.models.UserBooksPrimaryKey;
import com.joydeep.poc.repositories.UserBooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;

@Service
public class UserBookService {
    private final Logger logger = LoggerFactory.getLogger(UserBookService.class);
    private final UserBooksRepository userBooksRepository;

    public UserBookService(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }

    public void addReview(MultiValueMap<String,String> formData, OAuth2User principle){
        UserBooks userBooks=new UserBooks();
        UserBooksPrimaryKey userBooksPrimaryKey=new UserBooksPrimaryKey();
        userBooksPrimaryKey.setUserId(principle.getAttribute("login"));
        userBooksPrimaryKey.setBookId(formData.getFirst("bookId"));
        userBooks.setKey(userBooksPrimaryKey);
        userBooks.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userBooks.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userBooks.setReadingStatus(formData.getFirst("readingStatus"));
        userBooks.setRating(Integer.parseInt(formData.getFirst("rating")));

        userBooksRepository.save(userBooks);
        logger.info("Persisting review {}",userBooks);
    }
}
