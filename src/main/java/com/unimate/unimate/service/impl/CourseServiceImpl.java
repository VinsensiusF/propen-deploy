package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.CreateCourseContentDTO;
import com.unimate.unimate.dto.CreateCourseDTO;
import com.unimate.unimate.dto.UpdateCourseContentDTO;
import com.unimate.unimate.dto.UpdateCourseDTO;
import com.unimate.unimate.entity.Course;
import com.unimate.unimate.entity.CourseContent;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.exception.CourseContentNotFoundException;
import com.unimate.unimate.exception.CourseNotFoundException;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.repository.CourseContentRepository;
import com.unimate.unimate.repository.CourseRepository;
import com.unimate.unimate.service.CourseService;
import com.unimate.unimate.service.KelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseContentRepository courseContentRepository;
    private final KelasService kelasService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseContentRepository courseContentRepository, KelasService kelasService) {
        this.courseRepository = courseRepository;
        this.courseContentRepository = courseContentRepository;
        this.kelasService = kelasService;
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void saveCourseContent(CourseContent courseContent) {
        courseContentRepository.save(courseContent);
    }

    @Override
    public List<Course> getCourseByKelasId(Long id) {
        Kelas kelas = kelasService.getKelasById(id);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        return courseRepository.findCourseByKelasId(id);
    }

    @Override
    public List<CourseContent> getCourseContentByCourseId(Long id) {
        Course course = getCourseById(id);
        if (course == null) {
            throw new CourseNotFoundException();
        }
        return courseContentRepository.findCourseContentByCourseId(id);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findCourseById(id);
    }

    @Override
    public CourseContent getCourseContentById(Long id) {
        return courseContentRepository.findCourseContentById(id);
    }

    @Override
    public Course createCourse(CreateCourseDTO createCourseDTO) {
        Kelas kelas = kelasService.getKelasById(createCourseDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        Course course = new Course();
        course.setName(createCourseDTO.getName());
        course.setKelas(kelas);
        courseRepository.save(course);
        return course;
    }

    @Override
    public CourseContent createCourseContent(CreateCourseContentDTO createCourseContentDTO) {
        Course course = getCourseById(createCourseContentDTO.getCourseId());
        if (course == null) {
            throw new CourseNotFoundException();
        }
        CourseContent courseContent = new CourseContent();
        courseContent.setName(createCourseContentDTO.getName());
        courseContent.setType(createCourseContentDTO.getType());
        courseContent.setDescription(createCourseContentDTO.getDescription());
        courseContent.setLink(createCourseContentDTO.getLink());
        courseContent.setCourse(course);
        courseContentRepository.save(courseContent);
        return courseContent;
    }

    @Override
    public Course updateCourse(UpdateCourseDTO updateCourseDTO) {
        Course course = getCourseById(updateCourseDTO.getId());
        if (course == null) {
            throw new CourseNotFoundException();
        }

        Kelas kelas = kelasService.getKelasById(updateCourseDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        course.setKelas(kelas);
        course.setName(updateCourseDTO.getName());
        courseRepository.save(course);
        return course;
    }

    @Override
    public CourseContent updateCourseContent(UpdateCourseContentDTO updateCourseContentDTO) {
        CourseContent courseContent = getCourseContentById(updateCourseContentDTO.getId());
        if (courseContent == null) {
            throw new CourseContentNotFoundException();
        }

        Course course = getCourseById(updateCourseContentDTO.getCourseId());
        if (course == null) {
            throw new CourseNotFoundException();
        }

        courseContent.setCourse(course);
        courseContent.setName(updateCourseContentDTO.getName());
        courseContent.setType(updateCourseContentDTO.getType());
        courseContent.setDescription(updateCourseContentDTO.getDescription());
        courseContent.setLink(updateCourseContentDTO.getLink());
        courseContentRepository.save(courseContent);
        return courseContent;
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public void deleteCourseContent(CourseContent courseContent) {
        courseContentRepository.delete(courseContent);
    }
}
