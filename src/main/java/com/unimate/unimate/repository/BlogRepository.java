package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findBlogById(Long id);
}
