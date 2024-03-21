package com.unimate.unimate.service;

import com.unimate.unimate.dto.CreateCourseContentDTO;
import com.unimate.unimate.dto.CreateCourseDTO;
import com.unimate.unimate.dto.UpdateCourseContentDTO;
import com.unimate.unimate.dto.UpdateCourseDTO;
import com.unimate.unimate.entity.Course;
import com.unimate.unimate.entity.CourseContent;

import java.util.List;

public interface CourseService {
    void saveCourse(Course course);
    void saveCourseContent(CourseContent courseContent);

    List<Course> getCourseByKelasId(Long id);
    List<CourseContent> getCourseContentByCourseId(Long id);
    Course getCourseById(Long id);
    CourseContent getCourseContentById(Long id);

    Course createCourse(CreateCourseDTO createCourseDTO);

    CourseContent createCourseContent(CreateCourseContentDTO createCourseContentDTO);

    Course updateCourse(UpdateCourseDTO updateCourseDTO);

    CourseContent updateCourseContent(UpdateCourseContentDTO updateCourseContentDTO);

    void deleteCourse(Course course);
    void deleteCourseContent(CourseContent courseContent);


}
