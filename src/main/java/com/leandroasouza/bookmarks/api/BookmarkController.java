package com.leandroasouza.bookmarks.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandroasouza.bookmarks.model.Bookmark;
import com.leandroasouza.bookmarks.repository.BookmarkRepository;

@RestController
public class BookmarkController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookmarkController.class);

	private BookmarkRepository bookmarkRepository;
	private BookmarkResourceAssembler assembler;
		
	public BookmarkController(BookmarkRepository bookmarkRepository, BookmarkResourceAssembler assembler) {
		this.bookmarkRepository = bookmarkRepository;
		this.assembler = assembler;
	}
	
	@RequestMapping(value="/bookmarks", method=RequestMethod.GET)
	Resources<Resource<Bookmark>> getAll() {
		List<Resource<Bookmark>> bookmarks = bookmarkRepository.findAll()
														.stream()
														.map(assembler::toResource)
														.collect(Collectors.toList());
		return new Resources<>(bookmarks, 
								linkTo(methodOn(BookmarkController.class).getAll())
																		.withSelfRel());
	}

	@RequestMapping(value="/bookmarks", method=RequestMethod.POST)
	public ResponseEntity<?> createBookmark(@RequestBody Bookmark bookmark) throws URISyntaxException {
		LOGGER.debug("Executing post /bookmarks");
		Resource<Bookmark> resource = assembler.toResource(bookmarkRepository.save(bookmark));
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
	
	@RequestMapping(value="/bookmarks/{id}", method=RequestMethod.GET)
	public Resource<Bookmark> getOne(@PathVariable Long id) {
		Bookmark bookmark = bookmarkRepository.findById(id)
									.orElseThrow(() -> new BookmarkNotFoundException(id));
		
		return assembler.toResource(bookmark);
	}
	
	@RequestMapping(value="/bookmarks/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		
		bookmarkRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="bookmarks/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Bookmark newBookmark, @PathVariable Long id) throws URISyntaxException {
		
		Bookmark bookmark = bookmarkRepository.findById(id)
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
		
		Resource<Bookmark> resource = assembler.toResource(bookmark);
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
}
