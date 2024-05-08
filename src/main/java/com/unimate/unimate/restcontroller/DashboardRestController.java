package com.unimate.unimate.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.BlogService;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.ScholarshipService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    @Autowired
    KelasService kelasService;

    @Autowired
    AccountService accountService;

    @Autowired
    BlogService blogService;

    @Autowired
    ScholarshipService scholarshipService;

    @GetMapping("/admin")
    public ResponseEntity<?> dashboardAdmin() {
        Map<String, Long> admin = new HashMap<>();
        Long kelas = kelasService.getCountClass();
        Long account = accountService.getCountAccount();
        Long blog = blogService.getCountBlog();
        Long scholarship = scholarshipService.getCountScholarship();
        admin.put("kelas", kelas);
        admin.put("account", account);
        admin.put("artikel", blog);
        admin.put("scholarship", scholarship);
        return ResponseEntity.ok(admin);
    }
    
    
}
