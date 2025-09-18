package com.example.demo.Service;

import com.example.demo.repository.BlogsRepository;
import com.example.demo.model.Blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogsRepository blogRepository;

    @Autowired
    public BlogService(BlogsRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // Get all blogs
    public List<Blogs> getAllBlogs() {
        return blogRepository.findAll();
    }

    // Add a blog
    public Blogs addBlog(Blogs blog) {
        return blogRepository.save(blog);
    }

   
    public Optional<Blogs> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    
    public boolean deleteBlogById(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
