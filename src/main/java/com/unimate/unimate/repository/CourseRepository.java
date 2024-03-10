package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
}
