package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.entity.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
    ArrayList<BlogTag> findBlogTagsByBlog(Blog blog);

    void deleteAllByBlog(Blog blog);
}
