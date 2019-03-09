package com.leandroasouza.bookmarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leandroasouza.bookmarks.model.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{

}
