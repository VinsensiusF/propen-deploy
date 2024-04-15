package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.BlogResponseDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.enums.BlogType;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.service.BlogService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogRestController {

    private final BlogService blogService;

    @Autowired
    public BlogRestController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping(path = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> createBlog(@RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam String readingTime,
                                             @RequestParam BlogType type,
                                             @RequestParam ArrayList<String> blogTag,
                                             @RequestParam String writer,
                                             @RequestParam(required = false) MultipartFile image) throws IOException {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle(title);
        blogDTO.setContent(content);
        blogDTO.setReadingTime(readingTime);
        blogDTO.setType(type);
        blogDTO.setBlogTag(blogTag);
        blogDTO.setWriter(writer);
        if (image != null && !image.isEmpty()) {
            blogDTO.setImage(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        }
        Blog blog = blogService.createBlog(blogDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Blog has been created successfully.");
    }

    @GetMapping("/get")
    public BlogResponseDTO getBlogById(@RequestParam Long id) {
        return blogService.getBlogResponseDTOById(id);
    }

    @GetMapping("/get-all")
    public List<BlogResponseDTO> getAllBlog() {
        return blogService.getAllBlog();
    }

    @GetMapping("/search")
    public ArrayList<BlogResponseDTO> searchBlog(@RequestParam String keyword) {
        return blogService.findBlogByKeyword(keyword);
    }

    @DeleteMapping("/delete")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> deleteBlog(@RequestParam Long id) {
        Blog blog = blogService.getBlogById(id);
        blogService.deleteBlog(blog);
        return ResponseEntity.status(HttpStatus.OK).body("Blog has been deleted successfully.");
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> updateBlog(@RequestParam Long id,
                                             @RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam String readingTime,
                                             @RequestParam BlogType type,
                                             @RequestParam ArrayList<String> blogTag,
                                             @RequestParam String writer,
                                             @RequestParam(required = false) MultipartFile image) throws IOException {
        UpdateBlogDTO updateBlogDTO = new UpdateBlogDTO();
        updateBlogDTO.setId(id);
        updateBlogDTO.setTitle(title);
        updateBlogDTO.setContent(content);
        updateBlogDTO.setReadingTime(readingTime);
        updateBlogDTO.setType(type);
        updateBlogDTO.setBlogTag(blogTag);
        updateBlogDTO.setWriter(writer);
        if (image != null && !image.isEmpty()) {
            updateBlogDTO.setImage(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        }
        Blog blog = blogService.updateBlog(updateBlogDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Blog has been updated successfully.");
    }

    @GetMapping("/get-by-type")
    public List<BlogResponseDTO> getBlogsByType(@RequestParam String type) {
        switch (type) {
            case "informasi-beasiswa" -> {
                return blogService.getAllBlogByBlogType(BlogType.INFORMASI_BEASISWA);
            }
            case "pekerjaan-luar-negeri" -> {
                return blogService.getAllBlogByBlogType(BlogType.PEKERJAAN_LUAR_NEGERI);
            }
            case "tips-trik" -> {
                return blogService.getAllBlogByBlogType(BlogType.TIPS_TRIK);
            }
        }
        throw new EntityNotFoundException();
    }

    @GetMapping("/get-by-tags")
    public List<BlogResponseDTO> getRelatedBlogsByTags(@RequestParam ArrayList<String> tags) {
        return blogService.getAllBlogsByTags(tags);
    }

    @GetMapping("/get-top-5")
    public List<BlogResponseDTO> get5RecentBlogs() {
        return blogService.getAllBlogsDesc();
    }
}
