package com.leandroasouza.bookmarks.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leandroasouza.bookmarks.model.Bookmark;
import com.leandroasouza.bookmarks.repository.BookmarkRepository;

@RestController
public class BookmarkController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookmarkController.class);

	private BookmarkRepository bookmarkRepository;
		
	public BookmarkController(BookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}
	
	@GetMapping("/bookmarks")
	List<Bookmark> getAll() {
		return bookmarkRepository.findAll();
	}

	@PostMapping("/bookmarks")
	public Bookmark createBookmark(@RequestBody Bookmark bookmark) {
		LOGGER.debug("Executing post /bookmarks");
		return bookmarkRepository.save(bookmark);
	}
	
	@GetMapping("/bookmarks/{id}")
	public Optional<Bookmark> getOne(@PathVariable long id) {
		return bookmarkRepository.findById(id);
	}
	
	@DeleteMapping("/bookmarks/{id}")
	public void delete(@PathVariable long id) {
		bookmarkRepository.deleteById(id);
	}
	
	@PutMapping("bookmarks/{id}")
	public Bookmark update(@RequestBody Bookmark newBookmark, @PathVariable long id) {
		
		return bookmarkRepository.findById(id)
						.map(b -> {
							b.setName(newBookmark.getName());
							b.setDescription(newBookmark.getDescription());
							b.setUrl(newBookmark.getUrl());
							b.setTags(newBookmark.getTags());
							return bookmarkRepository.save(b);
						}).orElseGet(() -> {
							newBookmark.setId(id);
							return bookmarkRepository.save(newBookmark);
						});
	}
}
