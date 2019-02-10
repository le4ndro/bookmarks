package com.leandroasouza.bookmarks.api;

class BookmarkNotFoundException extends RuntimeException {
	
	public BookmarkNotFoundException(Long id) {
		super("Could not find bookmark " + id);
	}

}
