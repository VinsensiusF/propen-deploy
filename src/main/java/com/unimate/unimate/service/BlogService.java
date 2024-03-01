package com.unimate.unimate.service;

import com.unimate.unimate.entity.Blog;

import java.util.List;

public interface BlogService {
    void saveBlog(Blog blog);

    List<Blog> getAllBlog();

    Blog getBlogById(Long id);
}
