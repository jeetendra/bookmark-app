package com.jeet.bookmarkapp.service;

import com.jeet.bookmarkapp.entity.Bookmark;
import com.jeet.bookmarkapp.entity.User;
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

import static org.mockito.Mockito.any;

public class BookmarkServiceTest implements AutoCloseable {

    private AutoCloseable mocks;
    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserService userService;

    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        bookmarkService = new BookmarkServiceImpl(bookmarkRepository, userService);
    }

    @Test
    public void testBookmarkService() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testPaginatedBookmark() {
        //ARRANGE
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(1L, "Bookmark 1", "http://example.com", "Description 1", new User()));
        bookmarks.add(new Bookmark(2L, "Bookmark 2", "http://example.com", "Description 1", new User()));
        bookmarks.add(new Bookmark(3L, "Bookmark 3", "http://example.com", "Description 1", new User()));
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

    public void close() throws Exception {
        mocks.close();
    }
}
