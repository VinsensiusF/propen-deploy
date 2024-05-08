package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.BlogResponseDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.entity.BlogTag;
import com.unimate.unimate.enums.BlogType;
import com.unimate.unimate.exception.BlogNotFound;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.repository.BlogRepository;
import com.unimate.unimate.repository.BlogTagRepository;
import com.unimate.unimate.service.BlogService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogTagRepository blogTagRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, BlogTagRepository blogTagRepository){
        this.blogRepository = blogRepository;
        this.blogTagRepository = blogTagRepository;
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findBlogById(id);
    }

    @Override
    public void saveBlog(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Blog blog) {
        ArrayList<BlogTag> tags = blogTagRepository.findBlogTagsByBlog(blog);
        blogTagRepository.deleteAll(tags);
        blogRepository.delete(blog);
    }

    @Override
    @Transactional
    public List<BlogResponseDTO> getAllBlog() {
        List<BlogResponseDTO> blogResponseDTOs = new ArrayList<>();
        List<Blog> blogs =  blogRepository.findAll();
        for (Blog blog : blogs){
            BlogResponseDTO responseDTO = new BlogResponseDTO();
            ArrayList<BlogTag> tags = blogTagRepository.findBlogTagsByBlog(blog);
            ArrayList<String> tagsString = new ArrayList<>();
            for(BlogTag tag : tags){
                tagsString.add(tag.getTag());
            }
            responseDTO.setId(blog.getId());
            responseDTO.setTitle(blog.getTitle());
            responseDTO.setContent(blog.getContent());
            responseDTO.setWriter(blog.getWriter());
            responseDTO.setType(blog.getBlogType());
            responseDTO.setReadingTime(blog.getReadingTime());
            responseDTO.setBlogTag(tagsString);
            responseDTO.setCreatedAt(blog.getCreatedAt());
            try{
                if (blog.getImage() != null) {
                    responseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
                }
            } catch (SQLException e){
                log.error("Error reading blob", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blogResponseDTOs.add(responseDTO);
        }
        return blogResponseDTOs;
    }


    @Override
    @Transactional
    public BlogResponseDTO getBlogResponseDTOById(Long id) {
        Blog blog = blogRepository.findBlogById(id);
        if(blog == null){
            throw new EntityNotFoundException();
        }
        BlogResponseDTO blogResponseDTO = new BlogResponseDTO();
        ArrayList<BlogTag> tags = blogTagRepository.findBlogTagsByBlog(blog);
        ArrayList<String> tagString = new ArrayList<>();
        for(BlogTag tag : tags){
            tagString.add(tag.getTag());
        }
        blogResponseDTO.setId(blog.getId());
        blogResponseDTO.setTitle(blog.getTitle());
        blogResponseDTO.setContent(blog.getContent());
        blogResponseDTO.setWriter(blog.getWriter());
        blogResponseDTO.setType(blog.getBlogType());
        blogResponseDTO.setCreatedAt(blog.getCreatedAt());
        blogResponseDTO.setReadingTime(blog.getReadingTime());
        blogResponseDTO.setBlogTag(tagString);
        try{
            if (blog.getImage() != null) {
                blogResponseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
            }
        } catch (SQLException e){
            log.error("Error reading blob", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> blogTags = new ArrayList<>();
        for(BlogTag tag : tags){
            blogTags.add(tag.getTag());
        }
        blogResponseDTO.setBlogTag(blogTags);
        return blogResponseDTO;
    }

    @Override
    public Blog createBlog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setReadingTime(blogDTO.getReadingTime());
        blog.setBlogType(blogDTO.getType());
        blog.setWriter(blogDTO.getWriter());
        blog.setImage(blogDTO.getImage());
        saveBlog(blog);
        for(String tag : blogDTO.getBlogTag()){
            BlogTag blogTag = new BlogTag();
            blogTag.setTag(tag);
            blogTag.setBlog(blog);
            blogTagRepository.save(blogTag);
        }
        return blog;
    }

    //need change DTO
    @Override
    @Transactional
    public Blog updateBlog(UpdateBlogDTO updateBlogDTO) {

        Blog blog = getBlogById(updateBlogDTO.getId());
        if(blog == null){
            throw new BlogNotFound();
        }
        blog.setTitle(updateBlogDTO.getTitle());
        blog.setContent(updateBlogDTO.getContent());
        blog.setReadingTime(updateBlogDTO.getReadingTime());
        blog.setBlogType(updateBlogDTO.getType());
        blog.setWriter(updateBlogDTO.getWriter());
        blog.setImage(updateBlogDTO.getImage());

        blogTagRepository.deleteAllByBlog(blog);
        for(String dtoTag : updateBlogDTO.getBlogTag()) {
            BlogTag newTag = new BlogTag();
            newTag.setBlog(blog);
            newTag.setTag(dtoTag);
            blogTagRepository.save(newTag);
        }
        saveBlog(blog);

        return blog;
    }

    @Override
    @Transactional
    public ArrayList<BlogResponseDTO> findBlogByKeyword(String keyword) {
        if(keyword.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<Blog> blogs =  blogRepository.findBlogsByTitleContainingIgnoreCase(keyword);

        ArrayList<BlogResponseDTO> response = new ArrayList<>();
        for(Blog blog : blogs){
            BlogResponseDTO blogResponseDTO = new BlogResponseDTO();
            ArrayList<String> tagString= new ArrayList<>();
            ArrayList<BlogTag> tags = blogTagRepository.findBlogTagsByBlog(blog);
            for(BlogTag tag : tags){
                tagString.add(tag.getTag());
            }
            blogResponseDTO.setId(blog.getId());
            blogResponseDTO.setTitle(blog.getTitle());
            blogResponseDTO.setContent(blog.getContent());
            blogResponseDTO.setWriter(blog.getWriter());
            blogResponseDTO.setCreatedAt(blog.getCreatedAt());
            blogResponseDTO.setType(blog.getBlogType());
            blogResponseDTO.setReadingTime(blog.getReadingTime());
            blogResponseDTO.setBlogTag(tagString);
            try{
                if (blog.getImage() != null) {
                    blogResponseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
                }
            } catch (SQLException e){
                log.error("Error reading blob", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.add(blogResponseDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public List<BlogResponseDTO> getAllBlogByBlogType(BlogType blogType) {
         ArrayList<Blog> blogs = blogRepository.findBlogsByBlogType(blogType);

        ArrayList<BlogResponseDTO> response = new ArrayList<>();
        for(Blog blog : blogs){
            BlogResponseDTO blogResponseDTO = new BlogResponseDTO();
            ArrayList<String> tagString= new ArrayList<>();
            ArrayList<BlogTag> tags = blogTagRepository.findBlogTagsByBlog(blog);
            for(BlogTag tag : tags){
                tagString.add(tag.getTag());
            }
            blogResponseDTO.setId(blog.getId());
            blogResponseDTO.setTitle(blog.getTitle());
            blogResponseDTO.setContent(blog.getContent());
            blogResponseDTO.setWriter(blog.getWriter());
            blogResponseDTO.setCreatedAt(blog.getCreatedAt());
            blogResponseDTO.setType(blog.getBlogType());
            blogResponseDTO.setReadingTime(blog.getReadingTime());
            blogResponseDTO.setBlogTag(tagString);
            try{
                if (blog.getImage() != null) {
                    blogResponseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
                }
            } catch (SQLException e){
                log.error("Error reading blob", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.add(blogResponseDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public List<BlogResponseDTO> getAllBlogsByTags(ArrayList<String> tags) {
        // Find all blog tags matching the provided tags
        List<BlogTag> matchingTags = blogTagRepository.findAll()
                .stream()
                .filter(blogTag -> tags.stream()
                        .map(String::toLowerCase)
                        .anyMatch(tag -> tag.equalsIgnoreCase(blogTag.getTag())))
                .toList();

        // Extract unique blogs from matching tags
        List<Blog> relatedBlogs = matchingTags.stream()
                .map(BlogTag::getBlog)
                .distinct()
                .collect(Collectors.toList());

        ArrayList<BlogResponseDTO> response = new ArrayList<>();
        for(Blog blog : relatedBlogs){
            BlogResponseDTO blogResponseDTO = new BlogResponseDTO();
            blogResponseDTO.setId(blog.getId());
            blogResponseDTO.setTitle(blog.getTitle());
            blogResponseDTO.setContent(blog.getContent());
            blogResponseDTO.setWriter(blog.getWriter());
            blogResponseDTO.setCreatedAt(blog.getCreatedAt());
            blogResponseDTO.setType(blog.getBlogType());
            blogResponseDTO.setReadingTime(blog.getReadingTime());
            try{
                if (blog.getImage() != null) {
                    blogResponseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
                }
            } catch (SQLException e){
                log.error("Error reading blob", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.add(blogResponseDTO);
        }

        return response;
    }

//    @Override
//    public List<Blog> getAllBlogsDesc() {
//        ArrayList<Blog> blogs = blogRepository.findBlogsByOrderByCreatedAtDesc();
//        if (blogs.size() <= 5) {
//            return blogs;
//        } else {
//            return blogs.subList(0, 5);
//        }
//    }

    @Override
    @Transactional
    public List<BlogResponseDTO> getAllBlogsDesc() {
        ArrayList<BlogResponseDTO> response = new ArrayList<>();
        ArrayList<Blog> blogs = blogRepository.findBlogsByOrderByCreatedAtDesc();
        for(Blog blog : blogs){
            BlogResponseDTO blogResponseDTO = new BlogResponseDTO();
            blogResponseDTO.setId(blog.getId());
            blogResponseDTO.setTitle(blog.getTitle());
            blogResponseDTO.setContent(blog.getContent());
            blogResponseDTO.setReadingTime(blog.getReadingTime());
            blogResponseDTO.setCreatedAt(blog.getCreatedAt());
            blogResponseDTO.setType(blog.getBlogType());
            blogResponseDTO.setWriter(blog.getWriter());
            try{
                if (blog.getImage() != null) {
                    blogResponseDTO.setImage(Base64.getEncoder().encodeToString(blog.getImage().getBinaryStream().readAllBytes()));
                }
            } catch (SQLException e){
                log.error("Error reading blob", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            if (blog.getImage() != null && !blog.getImage().isEmpty()) {
//                blogDTO.setImage(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
//            }
            response.add(blogResponseDTO);
        }
        if (response.size() <= 5) {
            return response;
        } else {
            return response.subList(0, 5);
        }
    }


    @Override
    public Long getCountBlog() {
        return blogRepository.countAllBlog();
    }

}
