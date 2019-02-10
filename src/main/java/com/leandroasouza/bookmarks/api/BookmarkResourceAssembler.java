package com.leandroasouza.bookmarks.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.leandroasouza.bookmarks.model.Bookmark;

@Component
public class BookmarkResourceAssembler implements ResourceAssembler<Bookmark, Resource<Bookmark>> {

	@Override
	public Resource<Bookmark> toResource(Bookmark bookmark) {
		return new Resource<>(bookmark,
				linkTo(methodOn (BookmarkController.class).getOne(bookmark.getId())).withSelfRel(),
				linkTo(methodOn(BookmarkController.class).getAll()).withRel("bookmarks"));
	}
}
