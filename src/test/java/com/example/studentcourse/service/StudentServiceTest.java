package com.example.studentcourse.service;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.CourseRepository;
import com.example.studentcourse.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;
    private Course testCourse;

    @BeforeEach
    public void setup() {
        // Create test student
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Test Student");
        testStudent.setEmail("test.student@example.com");

        // Create test course
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setTitle("Test Course");
        testCourse.setDescription("Test Course Description");
    }

    @Test
    public void testSaveStudent() {
        // Setup
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // Execute
        Student savedStudent = studentService.save(new Student());

        // Verify
        assertNotNull(savedStudent);
        assertEquals("Test Student", savedStudent.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testSaveStudentWithDuplicateEmail() {
        // Setup
        when(studentRepository.save(any(Student.class))).thenThrow(DataIntegrityViolationException.class);

        // Execute and Verify
        assertThrows(DataIntegrityViolationException.class, () -> {
            studentService.save(new Student());
        });
    }

    @Test
    public void testGetAllStudents() {
        // Setup
        List<Student> students = Arrays.asList(testStudent, new Student());
        when(studentRepository.findAll()).thenReturn(students);

        // Execute
        List<Student> result = studentService.getAll();

        // Verify
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() {
        // Setup
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // Execute
        Student result = studentService.getById(1L);

        // Verify
        assertNotNull(result);
        assertEquals("Test Student", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStudentByIdNotFound() {
        // Setup
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getById(99L);
        });
    }

    @Test
    public void testUpdateStudent() {
        // Setup
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // Execute
        Student updatedStudent = studentService.update(testStudent);

        // Verify
        assertNotNull(updatedStudent);
        assertEquals("Test Student", updatedStudent.getName());
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testUpdateStudentNotFound() {
        // Setup
        when(studentRepository.existsById(99L)).thenReturn(false);
        testStudent.setId(99L);

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.update(testStudent);
        });
    }

    @Test
    public void testDeleteStudent() {
        // Setup
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // Execute
        studentService.delete(1L);

        // Verify
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteStudentNotFound() {
        // Setup
        when(studentRepository.existsById(99L)).thenReturn(false);

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.delete(99L);
        });
    }

    @Test
    public void testGetWithCourses() {
        // Setup
        Set<Course> courses = new HashSet<>();
        courses.add(testCourse);
        testStudent.setCourses(courses);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // Execute
        Student result = studentService.getWithCourses(1L);

        // Verify
        assertNotNull(result);
        assertEquals(1, result.getCourses().size());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStudentCourseData() {
        // Setup
        Object[] data = new Object[]{"Test Student", "Test Course"};
        List<Object[]> mockData = Collections.singletonList(data);
        
        when(studentRepository.fetchStudentCourseData()).thenReturn(mockData);

        // Execute
        List<Map<String, Object>> result = studentService.getStudentCourseData();

        // Verify
        assertEquals(1, result.size());
        assertEquals("Test Student", result.get(0).get("studentName"));
        assertEquals("Test Course", result.get(0).get("courseTitle"));
        verify(studentRepository, times(1)).fetchStudentCourseData();
    }

    @Test
    public void testRegisterStudentToCourse() {
        // Setup
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // Execute
        studentService.registerStudentToCourse(1L, 1L);

        // Verify
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testRegisterStudentToCourseStudentNotFound() {
        // Setup
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.registerStudentToCourse(99L, 1L);
        });
    }

    @Test
    public void testRegisterStudentToCourseCourseNotFound() {
        // Setup
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.registerStudentToCourse(1L, 99L);
        });
    }
}
