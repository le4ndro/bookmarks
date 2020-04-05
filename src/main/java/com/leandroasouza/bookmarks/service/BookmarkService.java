package com.leandroasouza.bookmarks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.leandroasouza.bookmarks.model.Bookmark;
import com.leandroasouza.bookmarks.repository.BookmarkRepository;

@Service
public class BookmarkService {

	@Autowired
	private BookmarkRepository repository;
	
	@Cacheable(value = "bookmarks")
	public List<Bookmark> findAll() {
		return repository.findAll();
	}
	
	@CachePut(value = "bookmarks", key = "#bookmark.id")
	public Bookmark save(Bookmark bookmark) {
		return repository.save(bookmark);
	}
	
	public Optional<Bookmark> findById(Long id) {
		return repository.findById(id);
	}
	
	@CacheEvict(value = "bookmarks", key = "#id")
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
}
