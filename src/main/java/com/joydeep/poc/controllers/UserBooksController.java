package com.joydeep.poc.controllers;

import com.joydeep.poc.services.UserBookService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map.Entry;

@Controller
@RequestMapping("/userbook")
public class UserBooksController {

    private final UserBookService userBookService;

    public UserBooksController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @PostMapping("/add")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principle) {
        if (principle == null || principle.getAttribute("login") == null) {
            return null;
        }
        System.out.println(formData);
        userBookService.addReview(formData,principle);
        return new ModelAndView("redirect:/books/" + formData.getFirst("bookId"));
    }

}
