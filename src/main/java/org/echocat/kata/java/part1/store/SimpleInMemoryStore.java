package org.echocat.kata.java.part1.store;

import org.echocat.kata.java.part1.domain.Author;
import org.echocat.kata.java.part1.domain.BasicPublishableEntity;
import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class SimpleInMemoryStore implements SimpleStore {
    private final Map<String, Book> books;
    private final Map<String, Magazine> magazines;
    private final Map<String, Author> authors;

    public SimpleInMemoryStore() throws IOException {
        this.authors = readAuthors();
        this.books = readBooks(authors);
        this.magazines = readMagazines(authors);
    }

    @Override
    public List<BasicPublishableEntity> getAllPublications() {
        List<BasicPublishableEntity> publications = new ArrayList<>(books.size() + magazines.size());
        publications.addAll(books.values());
        publications.addAll(magazines.values());
        return publications;
    }

    @Override
    public List<Book> getBookList() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Magazine> getMagazineList() {
        return new ArrayList<>(magazines.values());
    }

    @Override
    public Map<String, Book> getBooks() {
        return Collections.unmodifiableMap(books);
    }

    @Override
    public Map<String, Magazine> getMagazines() {
        return Collections.unmodifiableMap(magazines);
    }

    @Override
    public Map<String, Author> getAuthors() {
        return Collections.unmodifiableMap(authors);
    }

    @Override
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    @Override
    public void addMagazine(Magazine magazine) {
        magazines.put(magazine.getIsbn(), magazine);
    }

    private List<Author> findAuthors(Map<String, Author> authors, String emails) {
        return Arrays.stream(emails.split(",")).map(authors::get).collect(Collectors.toList());
    }

    private Map<String, Book> readBooks(Map<String, Author> authors) throws IOException {
        return readLinesColumns("org/echocat/kata/java/part1/data/books.csv")
                .map((columns) -> new Book(columns[0], columns[1], findAuthors(authors, columns[2]), columns[3]))
                .collect(Collectors.toMap(BasicPublishableEntity::getIsbn, Function.identity()));
    }

    private Map<String, Author> readAuthors() throws IOException {
        return readLinesColumns("org/echocat/kata/java/part1/data/authors.csv")
                .map((columns) -> new Author(columns[0], columns[1], columns[2]))
                .collect(Collectors.toMap(Author::getEmail, Function.identity()));
    }

    private Map<String, Magazine> readMagazines(Map<String, Author> authors) throws IOException {
        return readLinesColumns("org/echocat/kata/java/part1/data/magazines.csv")
                .map((columns) -> new Magazine(columns[0], columns[1], findAuthors(authors, columns[2]), columns[3]))
                .collect(Collectors.toMap(BasicPublishableEntity::getIsbn, Function.identity()));
    }

    private Stream<String[]> readLinesColumns(String s) throws IOException {
        return readResource(s)
                .stream()
                .skip(1)
                .map((line) -> line.split(";"));
    }

    private List<String> readResource(String path) throws IOException {
        File authorsFile = new ClassPathResource(path).getFile();
        return Files.readAllLines(authorsFile.toPath(), StandardCharsets.UTF_8);
    }

}
