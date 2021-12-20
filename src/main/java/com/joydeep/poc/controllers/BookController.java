package com.joydeep.poc.controllers;

import com.joydeep.poc.models.Book;
import com.joydeep.poc.services.EntityService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    private final EntityService<Book> bookService;

    public BookController(EntityService<Book> bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principle) {
        if(principle!=null && principle.getAttribute("login")!= null){
            model.addAttribute("loginId",principle.getAttribute("login"));
        }

        return bookService.getEntityPageById(bookId, model);
    }

}
