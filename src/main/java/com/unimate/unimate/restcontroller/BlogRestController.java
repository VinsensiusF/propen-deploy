package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.BlogDTO;
import com.unimate.unimate.dto.UpdateBlogDTO;
import com.unimate.unimate.entity.Blog;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogRestController {

    private final BlogService blogService;

    @Autowired
    public BlogRestController(BlogService blogService){
        this.blogService = blogService;
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> createBlog(@RequestBody BlogDTO blogDTO){
        Blog blog = blogService.createBlog(blogDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Blog has been created successfully.");
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public List<Blog> getAllBlog(){
        return blogService.getAllBlog();
    }

    @DeleteMapping("/delete")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> deleteBlog(@RequestParam Long id){
        Blog blog = blogService.getBlogById(id);
        blogService.deleteBlog(blog);
        return ResponseEntity.status(HttpStatus.OK).body("Blog has been deleted successfully.");
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> updateBlog(@RequestBody UpdateBlogDTO updateBlogDTO){
        Blog blog = blogService.updateBlog(updateBlogDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Blog has been updated successfully.");
    }
}
