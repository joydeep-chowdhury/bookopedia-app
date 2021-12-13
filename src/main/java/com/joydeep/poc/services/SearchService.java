package com.joydeep.poc.services;

import com.joydeep.poc.models.SearchResult;
import com.joydeep.poc.models.SearchResultBook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.joydeep.poc.utils.BookUtils.BOOK_COVER_TEMPLATE_URL;
import static com.joydeep.poc.utils.BookUtils.BOOK_SEARCH_TEMPLATE_URL;

@Service
public class SearchService {

    private final WebClient webClient;

    public SearchService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getSearchPageByQuery(String query, Model model) {
        SearchResult searchResult = webClient.get()
                                             .uri(uriBuilder -> uriBuilder.queryParam("q", query)
                                                                          .build())
                                             .retrieve()
                                             .bodyToMono(SearchResult.class)
                                             .block();

        List<SearchResultBook> searchResultBookList = searchResult.getDocs()
                                                                  .stream()
                                                                  .limit(10)
                                                                  .map(book -> {
                                                                      book.setKey(book.getKey()
                                                                                      .replaceAll("/works/", "")
                                                                                      .trim());
                                                                      String bookCoverFinalUrl = "/images/no-image.jpg";
                                                                      if (StringUtils.hasText(book.getCover_i())) {
                                                                          bookCoverFinalUrl = String.format(BOOK_SEARCH_TEMPLATE_URL, book.getCover_i());
                                                                      }
                                                                      book.setCover_i(bookCoverFinalUrl);
                                                                      return book;
                                                                  })
                                                                  .collect(Collectors.toList());

        model.addAttribute("searchResult", searchResultBookList);

        return "search";
    }
}
