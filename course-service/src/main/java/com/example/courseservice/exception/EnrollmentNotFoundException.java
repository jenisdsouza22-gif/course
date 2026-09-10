package com.example.courseservice.exception;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(String message) {
        super(message);
    }

    public EnrollmentNotFoundException(Long courseId, String studentId) {
        super("Student '" + studentId + "' is not enrolled in course with id: " + courseId);
    }
}
