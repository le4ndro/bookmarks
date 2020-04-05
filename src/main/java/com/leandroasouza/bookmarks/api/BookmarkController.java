package com.leandroasouza.bookmarks.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
import com.leandroasouza.bookmarks.service.BookmarkService;

@RestController
public class BookmarkController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookmarkController.class);

	private BookmarkResourceAssembler assembler;
	private BookmarkService service;
		
	public BookmarkController(BookmarkResourceAssembler assembler, BookmarkService service) {
		this.assembler = assembler;
		this.service = service;
	}
	
	@RequestMapping(value="/bookmarks", method=RequestMethod.GET)
	public Resources<Resource<Bookmark>> getAll() {
		List<Resource<Bookmark>> bookmarks = service.findAll()
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
		Resource<Bookmark> resource = assembler.toResource(service.save(bookmark));
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
	
	@RequestMapping(value="/bookmarks/{id}", method=RequestMethod.GET)
	public Resource<Bookmark> getOne(@PathVariable Long id) {
		Bookmark bookmark = service.findById(id)
									.orElseThrow(() -> new BookmarkNotFoundException(id));
		
		return assembler.toResource(bookmark);
	}
	
	@RequestMapping(value="/bookmarks/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="bookmarks/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Bookmark newBookmark, @PathVariable Long id) throws URISyntaxException {
		
		Bookmark bookmark = service.findById(id)
						.map(b -> {
							b.setName(newBookmark.getName());
							b.setDescription(newBookmark.getDescription());
							b.setUrl(newBookmark.getUrl());
							b.setTags(newBookmark.getTags());
							return service.save(b);
						}).orElseGet(() -> {
							newBookmark.setId(id);
							return service.save(newBookmark);
						});
		
		Resource<Bookmark> resource = assembler.toResource(bookmark);
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
}
