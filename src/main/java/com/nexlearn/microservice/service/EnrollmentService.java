package com.nexlearn.microservice.service;

import com.nexlearn.microservice.model.Enrollment;
import java.util.List;

public interface EnrollmentService {
    Enrollment enrollStudent(Long studentId, Long courseId);
    List<Enrollment> getAllEnrollments();
    List<Enrollment> getEnrollmentsByStudentId(Long studentId);
}
