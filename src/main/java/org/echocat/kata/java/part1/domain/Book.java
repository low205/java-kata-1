package org.echocat.kata.java.part1.domain;

import java.util.List;

public final class Book extends BasicPublishableEntity {
    private final String description;

    public Book(String title, String isbn, List<Author> authors, String description) {
        super(title, isbn, authors);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
