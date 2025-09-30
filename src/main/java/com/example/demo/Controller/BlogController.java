package com.example.demo.Controller;

import com.example.demo.Service.BlogService;
import com.example.demo.model.Blogs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
@Tag(name = "Blog APIs", description = "Operations related to Blog management")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    @Operation(summary = "Get all blogs", description = "Fetches the list of all blogs")
    public List<Blogs> getBlogs() {
        return blogService.getAllBlogs();
    }

//    @GetMapping("/{id}")
//    @Operation(summary = "Get blog by ID", description = "Fetches a single blog using its ID")
//    public ResponseEntity<Blogs> getBlogById(@PathVariable("id") Long id) {
//        Optional<Blogs> blog = blogService.getBlogById(id);
//        return blog.map(ResponseEntity::ok)
//                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }

  @GetMapping("/{id}")
  @Operation(summary = "Get blog by ID", description = "Fetches a single blog using its ID")
  public Blogs getBlogById(@PathVariable("id") Long id) {
      Blogs blog = blogService.getBlogById(id);
      return blog;
  }
    
    @PostMapping
    @Operation(summary = "Add a new blog", description = "Creates and returns the newly added blog")
    public ResponseEntity<Blogs> addBlog(@RequestBody Blogs blog) {
        Blogs savedBlog = blogService.addBlog(blog);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete blog by ID", description = "Deletes the blog with the given ID")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") Long id) {
        boolean deleted = blogService.deleteBlogById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
