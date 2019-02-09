package com.leandroasouza.bookmarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leandroasouza.bookmarks.model.*;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{

}
