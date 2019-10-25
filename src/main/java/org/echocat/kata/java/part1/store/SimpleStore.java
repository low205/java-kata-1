package org.echocat.kata.java.part1.store;

import org.echocat.kata.java.part1.domain.Author;
import org.echocat.kata.java.part1.domain.BasicPublishableEntity;
import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;

import java.util.List;
import java.util.Map;

public interface SimpleStore {
    List<BasicPublishableEntity> getAllPublications();

    List<Book> getBookList();

    List<Magazine> getMagazineList();

    Map<String, Book> getBooks();

    Map<String, Magazine> getMagazines();

    Map<String, Author> getAuthors();

    void addBook(Book book);

    void addMagazine(Magazine magazine);
}
