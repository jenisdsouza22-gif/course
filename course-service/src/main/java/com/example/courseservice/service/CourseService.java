package com.example.courseservice.service;

import com.example.courseservice.model.Course;

import java.util.List;

public interface CourseService {

    Course enrollCourse(Course course);

    List<Course> viewAllCourses();

    Course viewCourseById(Long id);

    Course updateCourse(Long id, Course course);

    void removeCourse(Long id);

    Course enrollStudent(Long courseId, String studentId);

    Course removeEnrollment(Long courseId, String studentId);
}
