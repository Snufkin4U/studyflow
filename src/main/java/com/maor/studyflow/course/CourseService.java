package com.maor.studyflow.course;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course createCourse(Course course) {
        course.setId(null);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = getCourseById(id);

        if (existingCourse == null) {
            return null;
        }

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setSemester(updatedCourse.getSemester());
        existingCourse.setDifficulty(updatedCourse.getDifficulty());

        return courseRepository.save(existingCourse);
    }

    public boolean deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);
        return true;
    }
}