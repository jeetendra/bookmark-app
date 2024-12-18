package com.jeet.bookmarkapp.service.impl;

import com.jeet.bookmarkapp.entity.Bookmark;
import com.jeet.bookmarkapp.repository.BookmarkRepository;
import com.jeet.bookmarkapp.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public Bookmark createBookmark(Bookmark bookmark) {

        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Bookmark updateBookmark(Bookmark updatedBookmark, Long id) {
        Bookmark bookmarkById = findBookmarkById(id);
        bookmarkById.setTitle(updatedBookmark.getTitle());
        bookmarkById.setDescription(updatedBookmark.getDescription());
        bookmarkById.setUrl(updatedBookmark.getUrl());
//        bookmarkById.setAuthor(updatedBookmark.getAuthor());
        return bookmarkRepository.save(bookmarkById);
    }

    @Override
    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    @Override
    public Page<Bookmark> getPaginatedBookmarks(Pageable pageable) {
        return bookmarkRepository.findAll(pageable);
    }

    @Override
    public List<Bookmark> findAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Override
    public Bookmark findBookmarkById(Long id) {
        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bookmark not found"));
    }
}
