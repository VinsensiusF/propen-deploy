package com.unimate.unimate.repository;

import com.unimate.unimate.entity.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {
    CourseContent findCourseContentById(Long id);
}
