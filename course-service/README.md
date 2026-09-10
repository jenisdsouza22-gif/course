# Course Service

A Spring Boot REST microservice to enroll, view, update, and remove course details.
Built with Spring Boot 3.3.4, Spring Data JPA, and an in-memory H2 database.

## Tech Stack
- Java 17
- Spring Boot 3.3.4 (Web, Data JPA, Validation)
- H2 in-memory database
- Maven

## Project Structure
```
course-service/
├── pom.xml
└── src/main/java/com/example/courseservice/
    ├── CourseServiceApplication.java
    ├── model/
    │   └── Course.java
    ├── repository/
    │   └── CourseRepository.java
    ├── service/
    │   ├── CourseService.java
    │   └── CourseServiceImpl.java
    ├── controller/
    │   └── CourseController.java
    └── exception/
        ├── CourseNotFoundException.java
        ├── EnrollmentNotFoundException.java
        ├── DuplicateCourseException.java
        ├── ErrorResponse.java
        └── GlobalExceptionHandler.java
```

## How to Run
```bash
mvn spring-boot:run
```
The service starts on `http://localhost:8080`.
H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:coursedb`, user: `sa`, no password)

## API Endpoints

| Operation                     | Method | URL                                       | Body                    |
|--------------------------------|--------|---------------------------------------------|--------------------------|
| Enroll a new course            | POST   | `/api/courses/enroll`                       | Course JSON (see below) |
| View all courses               | GET    | `/api/courses`                              | —                        |
| View course by ID              | GET    | `/api/courses/{id}`                         | —                        |
| Update a course                | PUT    | `/api/courses/{id}`                         | Course JSON              |
| Remove a course                | DELETE | `/api/courses/{id}`                         | —                        |
| Enroll a student in a course   | POST   | `/api/courses/{id}/enrollments/{studentId}` | —                        |
| Remove a student's enrollment  | DELETE | `/api/courses/{id}/enrollments/{studentId}` | —                        |

### Sample Course JSON (for enroll / update)
```json
{
  "courseCode": "CS101",
  "courseName": "Introduction to Computer Science",
  "instructor": "Dr. Alan Turing",
  "credits": 4,
  "description": "Fundamentals of programming and computer science.",
  "duration": "12 weeks"
}
```
> `enrolledStudents` is returned in responses but is managed only through the enrollment endpoints below — it isn't set directly via enroll/update.

### Sample cURL commands

**Enroll a course**
```bash
curl -X POST http://localhost:8080/api/courses/enroll \
  -H "Content-Type: application/json" \
  -d '{
        "courseCode": "CS101",
        "courseName": "Introduction to Computer Science",
        "instructor": "Dr. Alan Turing",
        "credits": 4,
        "description": "Fundamentals of programming and computer science.",
        "duration": "12 weeks"
      }'
```

**View all courses**
```bash
curl http://localhost:8080/api/courses
```

**View course by ID**
```bash
curl http://localhost:8080/api/courses/1
```

**Update a course**
```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d '{
        "courseCode": "CS101",
        "courseName": "Advanced Computer Science",
        "instructor": "Dr. Alan Turing",
        "credits": 5,
        "description": "Updated description.",
        "duration": "16 weeks"
      }'
```

**Remove a course**
```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

**Enroll a student in a course**
```bash
curl -X POST http://localhost:8080/api/courses/1/enrollments/S1001
```

**Remove a student's enrollment from a course**
```bash
curl -X DELETE http://localhost:8080/api/courses/1/enrollments/S1001
```

## Error Handling
Centralized via `GlobalExceptionHandler` (`@RestControllerAdvice`):

| Scenario                          | HTTP Status | Exception                  |
|------------------------------------|-------------|------------------------------|
| Course not found                   | 404         | `CourseNotFoundException`    |
| Student not enrolled (on remove)   | 404         | `EnrollmentNotFoundException`|
| Duplicate course code on enroll    | 409         | `DuplicateCourseException`   |
| Invalid/missing request fields     | 400         | `MethodArgumentNotValidException` |
| Any other unhandled error          | 500         | `Exception`                  |

Sample error response:
```json
{
  "timestamp": "2026-09-09T10:15:30",
  "status": 404,
  "error": "Not Found",
  "message": "Course not found with id: 5",
  "path": "/api/courses/5"
}
```
