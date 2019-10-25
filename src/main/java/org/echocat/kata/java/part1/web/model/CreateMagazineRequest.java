package org.echocat.kata.java.part1.web.model;

public class CreateMagazineRequest {
    private String title;
    private String isbn;
    private String authorsEmail;
    private String publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorsEmail() {
        return authorsEmail;
    }

    public void setAuthorsEmail(String authorsEmail) {
        this.authorsEmail = authorsEmail;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
