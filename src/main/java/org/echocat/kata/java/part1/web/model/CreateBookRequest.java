package org.echocat.kata.java.part1.web.model;

public class CreateBookRequest {
    private String title;
    private String isbn;
    private String authorsEmail;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
