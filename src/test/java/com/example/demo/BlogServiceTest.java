package com.example.demo;

import com.example.demo.Service.BlogService;
import com.example.demo.model.Blogs;
import com.example.demo.repository.BlogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BlogServiceTest {

    @Mock
    private BlogsRepository blogsRepository;

    @InjectMocks
    private BlogService blogService;

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
    void testGetAllBlogs() {
        when(blogsRepository.findAll()).thenReturn(Arrays.asList(blog1, blog2));

        List<Blogs> result = blogService.getAllBlogs();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("First Blog");
        verify(blogsRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Add blog - should save and return blog")
    void testAddBlog() {
        when(blogsRepository.save(blog1)).thenReturn(blog1);

        Blogs result = blogService.addBlog(blog1);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("First Blog");
        verify(blogsRepository, times(1)).save(blog1);
    }

    @Test
    @DisplayName("Get blog by ID - found")
    void testGetBlogByIdFound() {
        when(blogsRepository.findById(1L)).thenReturn(Optional.of(blog1));

        Optional<Blogs> result = blogService.getBlogById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("First Blog");
        verify(blogsRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get blog by ID - not found")
    void testGetBlogByIdNotFound() {
        when(blogsRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Blogs> result = blogService.getBlogById(99L);

        assertThat(result).isEmpty();
        verify(blogsRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Delete blog - blog exists")
    void testDeleteBlogByIdExists() {
        when(blogsRepository.existsById(1L)).thenReturn(true);

        boolean deleted = blogService.deleteBlogById(1L);

        assertThat(deleted).isTrue();
        verify(blogsRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete blog - blog does not exist")
    void testDeleteBlogByIdNotExists() {
        when(blogsRepository.existsById(99L)).thenReturn(false);

        boolean deleted = blogService.deleteBlogById(99L);

        assertThat(deleted).isFalse();
        verify(blogsRepository, never()).deleteById(99L);
    }
}
