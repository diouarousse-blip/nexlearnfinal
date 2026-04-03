package com.nexlearn.microservice.controller;

import com.nexlearn.microservice.model.Enrollment;
import com.nexlearn.microservice.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        if (enrollments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }
}
