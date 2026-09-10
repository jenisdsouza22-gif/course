package com.example.courseservice.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course code is mandatory")
    @Column(unique = true, nullable = false)
    private String courseCode;

    @NotBlank(message = "Course name is mandatory")
    private String courseName;

    @NotBlank(message = "Instructor name is mandatory")
    private String instructor;

    @Min(value = 1, message = "Credits must be at least 1")
    private int credits;

    private String description;

    private String duration;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_enrollments", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "student_id")
    private Set<String> enrolledStudents = new LinkedHashSet<>();

    public Course() {
    }

    public Course(Long id, String courseCode, String courseName, String instructor,
                  int credits, String description, String duration) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credits = credits;
        this.description = description;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
