package org.echocat.kata.java.part1.service;

import org.echocat.kata.java.part1.domain.BasicPublishableEntity;
import org.echocat.kata.java.part1.web.model.CreateBookRequest;
import org.echocat.kata.java.part1.web.model.CreateMagazineRequest;

import java.util.List;
import java.util.Optional;

public interface BookStoreService {
    List<BasicPublishableEntity> getAllPublications();

    Optional<BasicPublishableEntity> findByISBN(String isbn);

    List<BasicPublishableEntity> findByAuthorEmail(String email);

    void createBook(CreateBookRequest request);

    void createMagazine(CreateMagazineRequest request);
}
