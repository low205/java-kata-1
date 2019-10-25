package org.echocat.kata.java.part1.domain;

import java.util.List;

public class Magazine extends BasicPublishableEntity {
    private final String publishedAt;

    public Magazine(String title, String isbn, List<Author> authors, String publishedAt) {
        super(title, isbn, authors);
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
