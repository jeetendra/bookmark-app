package com.jeet.bookmarkapp.controller;

import com.jeet.bookmarkapp.entity.Bookmark;
import com.jeet.bookmarkapp.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

//    @GetMapping
//    public ResponseEntity<List<Bookmark>> findAll() {
//        return ResponseEntity.ok(bookmarkService.findAllBookmarks());
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Page<Bookmark>> getPaginatedBookmarks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Bookmark> paginatedBookmarks = bookmarkService.getPaginatedBookmarks(pageRequest);
        System.out.println("----------------------");
        System.out.println(paginatedBookmarks);
        return ResponseEntity.ok(paginatedBookmarks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookmarkService.findBookmarkById(id));
    }

    @PostMapping
    public ResponseEntity<Bookmark> save(@Valid @RequestBody Bookmark bookmark) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createBookmark(bookmark));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> update(@Valid @RequestBody Bookmark bookmark, @PathVariable Long id) {
        return ResponseEntity.ok(bookmarkService.updateBookmark(bookmark, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
    }


}
