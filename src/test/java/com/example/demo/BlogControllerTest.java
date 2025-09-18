package com.example.demo;

import com.example.demo.Controller.BlogController;
import com.example.demo.Service.BlogService;
import com.example.demo.model.Blogs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @InjectMocks
    private BlogController blogController;

    private Blogs blog1;
    private Blogs blog2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blog1 = new Blogs();
        blog1.setId(1L);
        blog1.setTitle("First Blog");
        blog1.setContent("This is the first blog");

        blog2 = new Blogs();
        blog2.setId(2L);
        blog2.setTitle("Second Blog");
        blog2.setContent("This is the second blog");
    }

    @Test
    @DisplayName("Get all blogs - should return list of blogs")
    void testGetBlogs() {
        when(blogService.getAllBlogs()).thenReturn(Arrays.asList(blog1, blog2));

        List<Blogs> blogs = blogController.getBlogs();

        assertThat(blogs).hasSize(2);
        assertThat(blogs.get(0).getTitle()).isEqualTo("First Blog");
        verify(blogService, times(1)).getAllBlogs();
    }

    @Test
    @DisplayName("Get blog by ID - blog found")
    void testGetBlogByIdFound() {
        when(blogService.getBlogById(1L)).thenReturn(Optional.of(blog1));

        ResponseEntity<Blogs> response = blogController.getBlogById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(blog1);
        verify(blogService, times(1)).getBlogById(1L);
    }

    @Test
    @DisplayName("Get blog by ID - blog not found")
    void testGetBlogByIdNotFound() {
        when(blogService.getBlogById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Blogs> response = blogController.getBlogById(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(blogService, times(1)).getBlogById(99L);
    }

    @Test
    @DisplayName("Add blog - should return created blog")
    void testAddBlog() {
        when(blogService.addBlog(blog1)).thenReturn(blog1);

        ResponseEntity<Blogs> response = blogController.addBlog(blog1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(blog1);
        verify(blogService, times(1)).addBlog(blog1);
    }

    @Test
    @DisplayName("Delete blog - blog found and deleted")
    void testDeleteBlogFound() {
        when(blogService.deleteBlogById(1L)).thenReturn(true);

        ResponseEntity<Void> response = blogController.deleteBlog(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(blogService, times(1)).deleteBlogById(1L);
    }

    @Test
    @DisplayName("Delete blog - blog not found")
    void testDeleteBlogNotFound() {
        when(blogService.deleteBlogById(99L)).thenReturn(false);

        ResponseEntity<Void> response = blogController.deleteBlog(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(blogService, times(1)).deleteBlogById(99L);
    }
}
