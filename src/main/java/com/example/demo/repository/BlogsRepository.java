package com.example.demo.repository;



import com.example.demo.model.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blogs, Long> {
}

