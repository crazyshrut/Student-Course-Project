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
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void testSaveStudent() {
        // Create a new student
        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("test@example.com");

        // Save the student
        Student savedStudent = studentRepository.save(student);

        // Verify the student was saved
        assertNotNull(savedStudent.getId());
        assertEquals("Test Student", savedStudent.getName());
        assertEquals("test@example.com", savedStudent.getEmail());
    }

    @Test
    public void testFindByEmail() {
        // Create and save a student
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail("john@example.com");
        studentRepository.save(student);

        // Find the student by email
        Student foundStudent = studentRepository.findByEmail("john@example.com");

        // Verify the student was found
        assertNotNull(foundStudent);
        assertEquals("John Doe", foundStudent.getName());
    }

    @Test
    public void testStudentCourseRelationship() {
        // Create a student
        Student student = new Student();
        student.setName("Course Test Student");
        student.setEmail("course.test@example.com");
        studentRepository.save(student);

        // Create two courses
        Course course1 = new Course();
        course1.setTitle("Test Course 1");
        course1.setDescription("Test Description 1");
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Test Course 2");
        course2.setDescription("Test Description 2");
        courseRepository.save(course2);

        // Enroll the student in both courses
        student.addCourse(course1);
        student.addCourse(course2);
        studentRepository.save(student);

        // Get the student with courses
        Student savedStudent = studentRepository.findById(student.getId()).orElse(null);
        assertNotNull(savedStudent);
        assertEquals(2, savedStudent.getCourses().size());
    }

    @Test
    public void testFetchStudentCourseData() {
        // Create a student
        Student student = new Student();
        student.setName("Data Test Student");
        student.setEmail("data.test@example.com");
        studentRepository.save(student);

        // Create a course
        Course course = new Course();
        course.setTitle("Data Test Course");
        course.setDescription("Data Test Description");
        courseRepository.save(course);

        // Enroll the student in the course
        student.addCourse(course);
        studentRepository.save(student);

        // Fetch student-course data
        List<Object[]> results = studentRepository.fetchStudentCourseData();
        
        // Verify the results
        assertFalse(results.isEmpty());
        boolean found = false;
        for (Object[] result : results) {
            if ("Data Test Student".equals(result[0]) && "Data Test Course".equals(result[1])) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Student-course relationship not found in query results");
    }

    @Test
    public void testFindStudentsByCourseId() {
        // Create students
        Student student1 = new Student();
        student1.setName("Student 1");
        student1.setEmail("student1@example.com");
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Student 2");
        student2.setEmail("student2@example.com");
        studentRepository.save(student2);

        // Create a course
        Course course = new Course();
        course.setTitle("Common Course");
        course.setDescription("Course for multiple students");
        courseRepository.save(course);

        // Enroll both students in the course
        student1.addCourse(course);
        studentRepository.save(student1);
        
        student2.addCourse(course);
        studentRepository.save(student2);

        // Find students by course id
        List<Student> students = studentRepository.findStudentsByCourseId(course.getId());
        
        // Verify the results
        assertEquals(2, students.size());
    }

    @Test
    public void testUniqueEmailConstraint() {
        // Create and save first student
        Student student1 = new Student();
        student1.setName("First Student");
        student1.setEmail("duplicate@example.com");
        studentRepository.save(student1);

        // Try to create another student with the same email
        Student student2 = new Student();
        student2.setName("Second Student");
        student2.setEmail("duplicate@example.com");
        
        // Verify that exception is thrown
        assertThrows(DataIntegrityViolationException.class, () -> {
            studentRepository.saveAndFlush(student2);
        });
    }
}
