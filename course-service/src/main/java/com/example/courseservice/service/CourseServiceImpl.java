package com.example.courseservice.service;

import com.example.courseservice.exception.CourseNotFoundException;
import com.example.courseservice.exception.DuplicateCourseException;
import com.example.courseservice.exception.EnrollmentNotFoundException;
import com.example.courseservice.model.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course enrollCourse(Course course) {

        if (courseRepository.existsByCourseCode(course.getCourseCode())) {

            System.out.println(">>> Course already exists");

            throw new DuplicateCourseException(
                    "Course already exists with code: " + course.getCourseCode());
        }

        Course savedCourse = courseRepository.save(course);

        System.out.println(">>> Course saved successfully: " + savedCourse.getId());

        return savedCourse;
    }

    @Override
    public List<Course> viewAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course viewCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        existingCourse.setCourseName(courseDetails.getCourseName());
        existingCourse.setInstructor(courseDetails.getInstructor());
        existingCourse.setCredits(courseDetails.getCredits());
        existingCourse.setDescription(courseDetails.getDescription());
        existingCourse.setDuration(courseDetails.getDuration());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void removeCourse(Long id) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        courseRepository.delete(existingCourse);
    }

    @Override
    public Course enrollStudent(Long courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        course.getEnrolledStudents().add(studentId);
        return courseRepository.save(course);
    }

    @Override
    public Course removeEnrollment(Long courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (!course.getEnrolledStudents().remove(studentId)) {
            throw new EnrollmentNotFoundException(courseId, studentId);
        }

        return courseRepository.save(course);
    }
}
