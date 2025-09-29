package com.example.demo;

import com.example.demo.model.Blogs;
import com.example.demo.repository.BlogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest 
@AutoConfigureMockMvc
class BlogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlogsRepository blogsRepository;

    @BeforeEach
    void setUp() {
        blogsRepository.deleteAll(); 
    }

    @Test
    @DisplayName("POST /blogs - should create a new blog")
    void testAddBlog() throws Exception {
        String blogJson = """
                {
                  "title": "Test Blog",
                  "content": "This is a test blog"
                }
                """;

        mockMvc.perform(post("/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Blog")))
                .andExpect(jsonPath("$.content", is("This is a test blog")));
    }

    @Test
    @DisplayName("GET /blogs - should return list of blogs")
    void testGetAllBlogs() throws Exception {
        Blogs blog = new Blogs();
        blog.setTitle("Sample Blog");
        blog.setContent("Some content");
        blogsRepository.save(blog);

        mockMvc.perform(get("/blogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Sample Blog")))
                .andExpect(jsonPath("$[0].content", is("Some content")));
    }

    @Test
    @DisplayName("GET /blogs/{id} - blog found")
    void testGetBlogByIdFound() throws Exception {
        Blogs blog = new Blogs();
        blog.setTitle("Found Blog");
        blog.setContent("Found content");
        Blogs saved = blogsRepository.save(blog);

        mockMvc.perform(get("/blogs/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Found Blog")));
    }

    @Test
    @DisplayName("GET /blogs/{id} - blog not found")
    void testGetBlogByIdNotFound() throws Exception {
        mockMvc.perform(get("/blogs/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /blogs/{id} - blog deleted")
    void testDeleteBlogById() throws Exception {
        Blogs blog = new Blogs();
        blog.setTitle("Delete Blog");
        blog.setContent("Delete content");
        Blogs saved = blogsRepository.save(blog);

        mockMvc.perform(delete("/blogs/{id}", saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /blogs/{id} - blog not found")
    void testDeleteBlogByIdNotFound() throws Exception {
        mockMvc.perform(delete("/blogs/{id}", 12345L))
                .andExpect(status().isNotFound());
    }
}
