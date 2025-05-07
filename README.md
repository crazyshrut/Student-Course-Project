# Student-Course Management System

A Spring Boot-based web application designed to manage students and courses using a many-to-many relationship. It showcases the development of a full-fledged web solution leveraging Spring Boot, Spring Data JPA, JSP for the view layer, and a MySQL database.

## Project Structure

```
student-course-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/studentcourse/
│   │   │       ├── config/
│   │   │       │   └── DataInitializer.java
│   │   │       ├── controller/
│   │   │       │   ├── CourseController.java
│   │   │       │   ├── HomeController.java
│   │   │       │   └── StudentController.java
│   │   │       ├── model/
│   │   │       │   ├── Course.java
│   │   │       │   └── Student.java
│   │   │       ├── repository/
│   │   │       │   ├── CourseRepository.java
│   │   │       │   └── StudentRepository.java
│   │   │       ├── service/
│   │   │       │   ├── CourseService.java
│   │   │       │   ├── CourseServiceImpl.java
│   │   │       │   ├── StudentService.java
│   │   │       │   └── StudentServiceImpl.java
│   │   │       └── StudentCourseApplication.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   │               ├── common/
│   │               │   ├── footer.jsp
│   │               │   └── header.jsp
│   │               ├── addCourse.jsp
│   │               ├── addStudent.jsp
│   │               ├── home.jsp
│   │               ├── listCourses.jsp
│   │               ├── listStudents.jsp
│   │               ├── updateCourse.jsp
│   │               ├── updateStudent.jsp
│   │               ├── viewCourse.jsp
│   │               └── viewStudent.jsp
│   └── test/
│       ├── java/
│       │   └── com/example/studentcourse/
│       │       ├── repository/
│       │       │   ├── CourseRepositoryTest.java
│       │       │   └── StudentRepositoryTest.java
│       │       └── service/
│       │           ├── CourseServiceTest.java
│       │           └── StudentServiceTest.java
│       └── resources/
│           └── application-test.properties
└── pom.xml
```

## Entity Relationship

The application models a many-to-many association between students and courses, where:

* A student can be enrolled in several courses  
* A course can have multiple students registered


## Features

1. **Student Management**:
   * Add new students
   * View list of all students
   * Update student information
   * Delete students
   * View student details with enrolled courses

2. **Course Management**:
   * Add new courses
   * View list of all courses
   * Update course information
   * Delete courses
   * View course details with enrolled students

3. **Enrollment Management**:
   * Register students for courses
   * Remove students from courses
   * View enrollments

## Technologies Used

* **Backend**: Spring Boot 2.7, Spring MVC, Spring Data JPA
* **Frontend**: JSP, JSTL, Bootstrap 5
* **Database**: MySQL
* **Testing**: JUnit 5, Mockito
* **Build Tool**: Maven

## Prerequisites

* Java 11 or higher
* Maven 3.6+
* MySQL 8.0+

## Running the Application

1. Clone the repository
2. Create a MySQL database:
   ```sql
   CREATE DATABASE student_course_db;
   ```
3. Configure the database settings in `application.properties` to match your environment.
4. Navigate to the project directory and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
5. Access the application at: http://localhost:8080

## Running Tests

```bash
mvn test
```

## Sample Data

The application is initialized with 10 students and 10 courses with various enrollments using the `DataInitializer` class that runs on application startup.

## Custom Query Example

The application includes a custom JPQL query in the `StudentRepository`:

```java
@Query("SELECT s.name as studentName, c.title as courseTitle FROM Student s JOIN s.courses c")
List<Object[]> fetchStudentCourseData();
```

This query fetches student names along with their enrolled course titles using a join operation between the entities.
