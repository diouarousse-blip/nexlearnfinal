package com.nexlearn.microservice.service.impl;

import com.nexlearn.microservice.model.Course;
import com.nexlearn.microservice.repository.CourseRepository;
import com.nexlearn.microservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        existingCourse.setCourseName(courseDetails.getCourseName());
        existingCourse.setCredits(courseDetails.getCredits());
        existingCourse.setMaxCapacity(courseDetails.getMaxCapacity());
        return courseRepository.save(existingCourse);
    }

    @Override
    public void incrementEnrollment(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        course.incrementEnrollment();
        courseRepository.save(course);
    }

    @Override
    public void decrementEnrollment(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        course.decrementEnrollment();
        courseRepository.save(course);
    }
}
