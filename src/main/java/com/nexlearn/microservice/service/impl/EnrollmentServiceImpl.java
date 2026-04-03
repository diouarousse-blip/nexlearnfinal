package com.nexlearn.microservice.service.impl;

import com.nexlearn.microservice.model.Course;
import com.nexlearn.microservice.model.Enrollment;
import com.nexlearn.microservice.model.Student;
import com.nexlearn.microservice.repository.EnrollmentRepository;
import com.nexlearn.microservice.repository.StudentRepository;
import com.nexlearn.microservice.service.CourseService;
import com.nexlearn.microservice.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseService courseService;

    @Override
    public Enrollment enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (course.getCurrentEnrollment() >= course.getMaxCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course is full");
        }

        Enrollment enrollment = new Enrollment(student, course, LocalDate.now());
        enrollmentRepository.save(enrollment);

        courseService.incrementEnrollment(courseId);

        return enrollment;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
