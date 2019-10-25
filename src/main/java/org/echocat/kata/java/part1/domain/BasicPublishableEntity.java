package org.echocat.kata.java.part1.domain;

import java.util.List;

public abstract class BasicPublishableEntity {
    private final String title;
    private final String isbn;
    private final List<Author> authors;

    public BasicPublishableEntity(String title, String isbn, List<Author> authors) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
