package com.example.studentcourse.repository;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveCourse() {
        // Create a new course
        Course course = new Course();
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        // Save the course
        Course savedCourse = courseRepository.save(course);

        // Verify the course was saved
        assertNotNull(savedCourse.getId());
        assertEquals("Test Course", savedCourse.getTitle());
        assertEquals("Test Description", savedCourse.getDescription());
    }

    @Test
    public void testFindByTitle() {
        // Create and save a course
        Course course = new Course();
        course.setTitle("Unique Title");
        course.setDescription("Test Description");
        courseRepository.save(course);

        // Find the course by title
        Course foundCourse = courseRepository.findByTitle("Unique Title");

        // Verify the course was found
        assertNotNull(foundCourse);
        assertEquals("Test Description", foundCourse.getDescription());
    }

    @Test
    public void testCoursesWithNoStudents() {
        // Create and save empty courses
        Course course1 = new Course();
        course1.setTitle("Empty Course 1");
        course1.setDescription("No students enrolled");
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Empty Course 2");
        course2.setDescription("No students enrolled");
        courseRepository.save(course2);

        // Create a course with a student
        Course course3 = new Course();
        course3.setTitle("Populated Course");
        course3.setDescription("Has students enrolled");
        courseRepository.save(course3);

        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("test.student@example.com");
        studentRepository.save(student);

        student.addCourse(course3);
        studentRepository.save(student);

        // Find courses with no students
        List<Course> emptyCourses = courseRepository.findCoursesWithNoStudents();

        // Verify the result
        assertFalse(emptyCourses.isEmpty());
        assertTrue(emptyCourses.stream().anyMatch(c -> "Empty Course 1".equals(c.getTitle())));
        assertTrue(emptyCourses.stream().anyMatch(c -> "Empty Course 2".equals(c.getTitle())));
        assertFalse(emptyCourses.stream().anyMatch(c -> "Populated Course".equals(c.getTitle())));
    }

    @Test
    public void testFindCoursesByStudentId() {
        // Create courses
        Course course1 = new Course();
        course1.setTitle("Student Course 1");
        course1.setDescription("First course for student");
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Student Course 2");
        course2.setDescription("Second course for student");
        courseRepository.save(course2);

        // Create a student
        Student student = new Student();
        student.setName("Course Student");
        student.setEmail("course.student@example.com");
        studentRepository.save(student);

        // Enroll the student in both courses
        student.addCourse(course1);
        student.addCourse(course2);
        studentRepository.save(student);

        // Find courses by student id
        List<Course> courses = courseRepository.findCoursesByStudentId(student.getId());

        // Verify the results
        assertEquals(2, courses.size());
        assertTrue(courses.stream().anyMatch(c -> "Student Course 1".equals(c.getTitle())));
        assertTrue(courses.stream().anyMatch(c -> "Student Course 2".equals(c.getTitle())));
    }

    @Test
    public void testCountStudentsPerCourse() {
        // Create courses
        Course course1 = new Course();
        course1.setTitle("Popular Course");
        course1.setDescription("Course with multiple students");
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Unpopular Course");
        course2.setDescription("Course with one student");
        courseRepository.save(course2);

        // Create students
        Student student1 = new Student();
        student1.setName("Student 1");
        student1.setEmail("student1@example.com");
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Student 2");
        student2.setEmail("student2@example.com");
        studentRepository.save(student2);

        Student student3 = new Student();
        student3.setName("Student 3");
        student3.setEmail("student3@example.com");
        studentRepository.save(student3);

        // Enroll students in courses
        student1.addCourse(course1);
        studentRepository.save(student1);

        student2.addCourse(course1);
        studentRepository.save(student2);

        student3.addCourse(course2);
        studentRepository.save(student3);

        // Count students per course
        List<Object[]> results = courseRepository.countStudentsPerCourse();

        // Verify the results
        assertFalse(results.isEmpty());
        
        for (Object[] result : results) {
            Long courseId = (Long) result[0];
            String courseTitle = (String) result[1];
            Long studentCount = (Long) result[2];
            
            if (courseTitle.equals("Popular Course")) {
                assertEquals(2L, studentCount);
            } else if (courseTitle.equals("Unpopular Course")) {
                assertEquals(1L, studentCount);
            }
        }
    }

    @Test
    public void testUniqueTitleConstraint() {
        // Create and save first course
        Course course1 = new Course();
        course1.setTitle("Duplicate Title");
        course1.setDescription("First course description");
        courseRepository.save(course1);

        // Try to create another course with the same title
        Course course2 = new Course();
        course2.setTitle("Duplicate Title");
        course2.setDescription("Second course description");
        
        // Verify that exception is thrown
        assertThrows(DataIntegrityViolationException.class, () -> {
            courseRepository.saveAndFlush(course2);
        });
    }
}
