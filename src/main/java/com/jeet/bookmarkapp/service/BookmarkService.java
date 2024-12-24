package com.jeet.bookmarkapp.service;

import com.jeet.bookmarkapp.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookmarkService {
    Bookmark createBookmark(Bookmark bookmark);

    Bookmark updateBookmark(Bookmark bookmark, Long id);

    void deleteBookmark(Long bookmarkId);

    List<Bookmark> findAllBookmarks();

    Bookmark findBookmarkById(Long id);

    Page<Bookmark> getPaginatedBookmarks(Pageable pageable);
}
