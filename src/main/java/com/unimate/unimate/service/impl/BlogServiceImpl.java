package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.exception.BlogNotFound;
import com.unimate.unimate.repository.BlogRepository;
import com.unimate.unimate.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    @Override
    public void saveBlog(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Blog blog) {
        blogRepository.delete(blog);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findBlogById(id);
    }

    @Override
    public Blog createBlog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        saveBlog(blog);
        return blog;
    }

    @Override
    public Blog updateBlog(UpdateBlogDTO updateBlogDTO) {
        Blog blog = getBlogById(updateBlogDTO.getId());
        if(blog == null){
            throw new BlogNotFound();
        }
        blog.setTitle(updateBlogDTO.getTitle());
        blog.setContent(updateBlogDTO.getContent());
        saveBlog(blog);

        return blog;
    }
}
