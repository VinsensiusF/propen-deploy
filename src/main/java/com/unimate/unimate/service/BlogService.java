package com.unimate.unimate.service;

import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;

import java.util.ArrayList;
import java.util.List;

public interface BlogService {
    void saveBlog(Blog blog);

    void deleteBlog(Blog blog);

    List<Blog> getAllBlog();

    Blog getBlogById(Long id);

    Blog createBlog(BlogDTO blogDTO);

    Blog updateBlog(UpdateBlogDTO updateBlogDTO);
}
