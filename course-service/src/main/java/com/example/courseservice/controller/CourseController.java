package com.example.courseservice.controller;

import com.example.courseservice.model.Course;
import com.example.courseservice.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Enroll (create) a new course
    // POST /api/courses/enroll
    @PostMapping("/enroll")
    public ResponseEntity<Course> enrollCourse(@Valid @RequestBody Course course) {
        Course savedCourse = courseService.enrollCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    // View all courses
    // GET /api/courses
    @GetMapping
    public ResponseEntity<List<Course>> viewAllCourses() {
        return ResponseEntity.ok(courseService.viewAllCourses());
    }

    // View a single course by id
    // GET /api/courses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Course> viewCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.viewCourseById(id));
    }

    // Update an existing course
    // PUT /api/courses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                                @Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    // Remove (delete) a course
    // DELETE /api/courses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCourse(@PathVariable Long id) {
        courseService.removeCourse(id);
        return ResponseEntity.noContent().build();
    }

    // Enroll a student into a course
    // POST /api/courses/{id}/enrollments/{studentId}
    @PostMapping("/{id}/enrollments/{studentId}")
    public ResponseEntity<Course> enrollStudent(@PathVariable Long id,
                                                 @PathVariable String studentId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.enrollStudent(id, studentId));
    }

    // Remove a student's enrollment from a course
    // DELETE /api/courses/{id}/enrollments/{studentId}
    @DeleteMapping("/{id}/enrollments/{studentId}")
    public ResponseEntity<Course> removeEnrollment(@PathVariable Long id,
                                                     @PathVariable String studentId) {
        return ResponseEntity.ok(courseService.removeEnrollment(id, studentId));
    }
}
