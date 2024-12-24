package com.jeet.bookmarkapp.controller;

import com.jeet.bookmarkapp.entity.Bookmark;
import com.jeet.bookmarkapp.entity.User;
import com.jeet.bookmarkapp.service.BookmarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(BookmarkController.class)
public class BookmarkControllerTest implements AutoCloseable {

    MockMvc mockMvc;
    @Mock
    BookmarkService bookmarkService;
    private AutoCloseable mocks;
    private BookmarkController bookmarkController;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        bookmarkController = new BookmarkController(bookmarkService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
    }

    @Test
    public void testGetAllBookmarks() throws Exception {
        // Mocking bookmark data
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(1L, "Bookmark 1", "Author 1", "Description 1", new User()));
        bookmarks.add(new Bookmark(2L, "Bookmark 2", "Author 2", "Description 2", new User()));

        // Mocking paginated response
        Page<Bookmark> page = new PageImpl<>(
                bookmarks,               // Page content
                PageRequest.of(0, 2),    // Pagination info (page 0, size 2)
                10                       // Total elements
        );
        Mockito.when(bookmarkService.getPaginatedBookmarks(any(PageRequest.class))).thenReturn(page);

        // Performing GET request and asserting the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookmarks")
                        .param("page", "0") // Set pagination params
                        .param("size", "2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk()) // HTTP 200 response
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray()) // Content should be an array
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2)) // Verify 2 items in content
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Bookmark 1")) // First item's title
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title").value("Bookmark 2")) // Second item's title
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(false)); // Verify 'empty' field is false
    }

    @Test
    public void testGetBookmarkById() throws Exception {
        Long id = 1L;
        Bookmark bookmark = new Bookmark(1L, "Bookmark 1", "Author 1", "Description 1", new User());

        Mockito.when(bookmarkService.findBookmarkById(anyLong())).thenReturn(bookmark);

        mockMvc.perform(get("/api/v1/bookmarks/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bookmark 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"))
                .andDo(print());

    }

    public void close() throws Exception {
        mocks.close(); // Ensures proper cleanup of resources
    }
}
