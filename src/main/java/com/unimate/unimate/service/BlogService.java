package com.unimate.unimate.service;

import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.BlogResponseDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.enums.BlogType;

import java.util.ArrayList;
import java.util.List;

public interface BlogService {

    Blog getBlogById(Long id);
    void saveBlog(Blog blog);

    void deleteBlog(Blog blog);

    List<BlogResponseDTO> getAllBlog();

    BlogResponseDTO getBlogResponseDTOById(Long id);

    Blog createBlog(BlogDTO blogDTO);

    Blog updateBlog(UpdateBlogDTO updateBlogDTO);

    ArrayList<BlogResponseDTO> findBlogByKeyword(String keyword);

    List<BlogResponseDTO> getAllBlogByBlogType(BlogType blogType);

    List<BlogResponseDTO> getAllBlogsByTags(ArrayList<String> tags);

    List<BlogResponseDTO> getAllBlogsDesc();
}
