package com.jeet.bookmarkapp.service;

import com.jeet.bookmarkapp.entity.Bookmark;
import com.jeet.bookmarkapp.repository.BookmarkRepository;
import com.jeet.bookmarkapp.service.impl.BookmarkServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

//import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookmarkService = new BookmarkServiceImpl(bookmarkRepository);
    }

    @Test
    public void testBookmarkService() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testPaginatedBookmark() {
        //ARRANGE
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(1L, "Bookmark 1", "http://example.com", "Description 1"));
        bookmarks.add(new Bookmark(2L, "Bookmark 2", "http://example.com", "Description 1"));
        bookmarks.add(new Bookmark(3L, "Bookmark 3", "http://example.com", "Description 1"));
        Page<Bookmark> bookmarkPage = new PageImpl<>(bookmarks);
        PageRequest pageRequest = PageRequest.of(0, 2);
        Mockito.when(bookmarkRepository.findAll(pageRequest)).thenReturn(bookmarkPage);

        //ACT
        Page<Bookmark> paginatedBookmarks = bookmarkService.getPaginatedBookmarks(pageRequest);

        //ASSERT
        Assertions.assertEquals(3, paginatedBookmarks.getTotalElements());



    }

    @Test
    public void testCreateBookmark() {
        // Setup mock data
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("Spring Documentation");
        bookmark.setUrl("https://spring.io/docs");
        bookmark.setDescription("Official Spring Docs");

        // Mock repository save
        Mockito.when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        // Call the service method
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark);

        // Assert the result
        Assertions.assertNotNull(createdBookmark);
        Assertions.assertEquals("Spring Documentation", createdBookmark.getTitle());
    }
}