package com.leandroasouza.bookmarks.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.leandroasouza.bookmarks.model.Bookmark;
import com.leandroasouza.bookmarks.repository.BookmarkRepository;
import com.leandroasouza.bookmarks.service.BookmarkService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookmarkController.class, 
			includeFilters = @ComponentScan.Filter(
					value = BookmarkResourceAssembler.class, 
					type = FilterType.ASSIGNABLE_TYPE))
public class BookmarkControllerTest {
	
	private static final long TEST_BOOKMARK_ID = 1L;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookmarkService bookmarks;
	
	@Before
	public void Setup() {
		Bookmark bookmark = new Bookmark();
		bookmark.setId(TEST_BOOKMARK_ID);
		bookmark.setName("Test");
		bookmark.setDescription("Test");
		bookmark.setUrl("http://test.com");
		bookmark.setTags("test");
		Optional<Bookmark> b = Optional.of(new Bookmark());
		given(this.bookmarks.findById(TEST_BOOKMARK_ID)).willReturn(b);
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		mockMvc.perform(get("/bookmarks/{id}", TEST_BOOKMARK_ID)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAll() throws Exception {
		
		mockMvc.perform(get("/bookmarks/")).andExpect(status().isOk());
	}
}
