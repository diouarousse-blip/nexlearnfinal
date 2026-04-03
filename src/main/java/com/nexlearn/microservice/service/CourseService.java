package com.nexlearn.microservice.service;

import com.nexlearn.microservice.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course createCourse(Course course);
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course updateCourse(Long id, Course courseDetails);
    void incrementEnrollment(Long courseId);
    void decrementEnrollment(Long courseId);
}
