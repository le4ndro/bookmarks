package com.leandroasouza.bookmarks.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookmarkNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(BookmarkNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String bookmarkNotFoundHandler(BookmarkNotFoundException ex) {
		return ex.getMessage();
	}
}
