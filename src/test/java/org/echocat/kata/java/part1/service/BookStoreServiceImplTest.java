package org.echocat.kata.java.part1.service;

import org.echocat.kata.java.part1.domain.BasicPublishableEntity;
import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.store.SimpleStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class BookStoreServiceImplTest {
    public SimpleStore simpleStoreMock;
    public BookStoreServiceImpl underTest;

    @Before
    public void setup() {
        simpleStoreMock = Mockito.mock(SimpleStore.class);
        underTest = new BookStoreServiceImpl(simpleStoreMock);
    }

    @Test
    public void shouldReturnAllPublicationsSortedByTitle() {
        List<BasicPublishableEntity> basicPublishableEntities = new ArrayList<>(2);
        basicPublishableEntities.add(new Book("A", "", Collections.emptyList(), ""));
        basicPublishableEntities.add(new Magazine("B", "", Collections.emptyList(), ""));
        when(simpleStoreMock.getAllPublications()).thenReturn(basicPublishableEntities);

        List<BasicPublishableEntity> allPublications = underTest.getAllPublications();
        Assert.assertEquals(2, allPublications.size());
        Assert.assertTrue(allPublications.get(0) instanceof Book);
        Assert.assertTrue(allPublications.get(1) instanceof Magazine);
    }

    @Test
    public void shouldReturnAllPublicationsSortedByTitleReverse() {
        List<BasicPublishableEntity> basicPublishableEntities = new ArrayList<>(2);
        basicPublishableEntities.add(new Book("B", "", Collections.emptyList(), ""));
        basicPublishableEntities.add(new Magazine("A", "", Collections.emptyList(), ""));
        when(simpleStoreMock.getAllPublications()).thenReturn(basicPublishableEntities);

        List<BasicPublishableEntity> allPublications = underTest.getAllPublications();
        Assert.assertEquals(2, allPublications.size());
        Assert.assertTrue(allPublications.get(0) instanceof Magazine);
        Assert.assertTrue(allPublications.get(1) instanceof Book);
    }
}

