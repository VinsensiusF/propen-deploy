package com.unimate.unimate.repository;

import com.unimate.unimate.entity.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {
    CourseContent findCourseContentById(Long id);
    @Query("SELECT cc FROM CourseContent cc WHERE cc.course.id = :courseId")
    List<CourseContent> findCourseContentByCourseId(@Param("courseId") Long courseId);
}
