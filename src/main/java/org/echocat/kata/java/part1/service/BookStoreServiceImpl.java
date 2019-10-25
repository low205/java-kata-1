package org.echocat.kata.java.part1.service;

import org.echocat.kata.java.part1.domain.Author;
import org.echocat.kata.java.part1.domain.BasicPublishableEntity;
import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.store.SimpleStore;
import org.echocat.kata.java.part1.web.model.CreateBookRequest;
import org.echocat.kata.java.part1.web.model.CreateMagazineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookStoreServiceImpl implements BookStoreService {

    private final static Comparator<BasicPublishableEntity> BY_TITLE = Comparator.comparing(BasicPublishableEntity::getTitle);
    private final SimpleStore simpleStore;

    @Autowired
    public BookStoreServiceImpl(SimpleStore simpleStore) {
        this.simpleStore = simpleStore;
    }

    @Override
    public List<BasicPublishableEntity> getAllPublications() {
        List<BasicPublishableEntity> allPublications = simpleStore.getAllPublications();
        allPublications.sort(BY_TITLE);
        return allPublications;
    }

    @Override
    public Optional<BasicPublishableEntity> findByISBN(String isbn) {
        Optional<BasicPublishableEntity> maybeBook = Optional.ofNullable(simpleStore.getBooks().get(isbn));
        Optional<BasicPublishableEntity> maybeMagazine = Optional.ofNullable(simpleStore.getMagazines().get(isbn));
        if (maybeBook.isPresent()) {
            return maybeBook;
        } else {
            return maybeMagazine;
        }
    }

    @Override
    public List<BasicPublishableEntity> findByAuthorEmail(String email) {
        if (email == null) {
            return Collections.emptyList();
        }
        return simpleStore.getAllPublications()
                .stream()
                .filter((publication) -> publication.getAuthors().stream().anyMatch((author -> Objects.equals(author.getEmail(), email))))
                .collect(Collectors.toList());
    }

    @Override
    public void createBook(CreateBookRequest request) {
        List<Author> authors = Arrays.stream(request.getAuthorsEmail().split(",")).map(simpleStore.getAuthors()::get).collect(Collectors.toList());
        simpleStore.addBook(new Book(request.getTitle(), request.getIsbn(), authors, request.getDescription()));
    }

    @Override
    public void createMagazine(CreateMagazineRequest request) {
        List<Author> authors = Arrays.stream(request.getAuthorsEmail().split(",")).map(simpleStore.getAuthors()::get).collect(Collectors.toList());
        simpleStore.addMagazine(new Magazine(request.getTitle(), request.getIsbn(), authors, request.getPublishedAt()));
    }
}
