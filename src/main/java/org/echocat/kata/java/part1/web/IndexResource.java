package org.echocat.kata.java.part1.web;

import org.echocat.kata.java.part1.domain.Author;
import org.echocat.kata.java.part1.service.BookStoreService;
import org.echocat.kata.java.part1.web.model.CreateBookRequest;
import org.echocat.kata.java.part1.web.model.CreateMagazineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

@Controller
public class IndexResource {

    private final BookStoreService bookStoreService;

    @Autowired
    public IndexResource(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @PostMapping(path = "/books")
    public String createBook(@ModelAttribute CreateBookRequest request) {
        bookStoreService.createBook(request);
        return "index";
    }

    @PostMapping(path = "/magazines")
    public String createBook(@ModelAttribute CreateMagazineRequest request) {
        bookStoreService.createMagazine(request);
        return "index";
    }

    @GetMapping(path = "/publications/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadPublications() throws UnsupportedEncodingException {
        String content =
                "title;isbn;authors\n" +
                        bookStoreService.getAllPublications().stream()
                                .map((entity) -> entity.getTitle() + ";" + entity.getIsbn() + ";" + entity.getAuthors().stream().map(Author::getEmail).collect(Collectors.joining(",")))
                                .collect(Collectors.joining("\n"));
        ByteArrayResource resource = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8.name()));
        return ResponseEntity.ok()
                .headers((headers) -> headers.setContentDisposition(ContentDisposition.builder("attachment").filename("publicactions.csv").build()))
                .contentLength(resource.contentLength()) //
                .body(resource);
    }

    @GetMapping(path = {"/", "/index.html"})
    public String index(Model model,
                        @RequestParam(value = "isbn", required = false) String isbn,
                        @RequestParam(value = "authorEmail", required = false) String authorEmail) {
        try {
            model.addAttribute("publications", Collections.emptyList());
            model.addAttribute("createBookRequest", new CreateBookRequest());
            model.addAttribute("createMagazineRequest", new CreateMagazineRequest());
            if (isbn == null && authorEmail == null) {
                model.addAttribute("publications", bookStoreService.getAllPublications());
            } else if (isbn != null) {
                bookStoreService.findByISBN(isbn).ifPresent((publication) ->
                        model.addAttribute("publications", Collections.singletonList(publication))
                );
            } else {
                model.addAttribute("publications", bookStoreService.findByAuthorEmail(authorEmail));
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "index";
    }
}
