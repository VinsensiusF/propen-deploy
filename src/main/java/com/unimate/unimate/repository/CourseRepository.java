package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
    @Query("SELECT c FROM Course c WHERE c.kelas.id = :kelasId")
    List<Course> findCourseByKelasId(@Param("kelasId") Long kelasId);
}
