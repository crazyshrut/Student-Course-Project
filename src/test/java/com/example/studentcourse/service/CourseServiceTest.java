package com.example.studentcourse.service;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.CourseRepository;
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
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course testCourse;
    private Student testStudent;

    @BeforeEach
    public void setup() {
        // Create test course
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setTitle("Test Course");
        testCourse.setDescription("Test Course Description");

        // Create test student
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Test Student");
        testStudent.setEmail("test.student@example.com");
    }

    @Test
    public void testSaveCourse() {
        // Setup
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // Execute
        Course savedCourse = courseService.save(new Course());

        // Verify
        assertNotNull(savedCourse);
        assertEquals("Test Course", savedCourse.getTitle());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testSaveCourseWithDuplicateTitle() {
        // Setup
        when(courseRepository.save(any(Course.class))).thenThrow(DataIntegrityViolationException.class);

        // Execute and Verify
        assertThrows(DataIntegrityViolationException.class, () -> {
            courseService.save(new Course());
        });
    }

    @Test
    public void testGetAllCourses() {
        // Setup
        List<Course> courses = Arrays.asList(testCourse, new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Execute
        List<Course> result = courseService.getAll();

        // Verify
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetCourseById() {
        // Setup
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));

        // Execute
        Course result = courseService.getById(1L);

        // Verify
        assertNotNull(result);
        assertEquals("Test Course", result.getTitle());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCourseByIdNotFound() {
        // Setup
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.getById(99L);
        });
    }

    @Test
    public void testUpdateCourse() {
        // Setup
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // Execute
        Course updatedCourse = courseService.update(testCourse);

        // Verify
        assertNotNull(updatedCourse);
        assertEquals("Test Course", updatedCourse.getTitle());
        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testUpdateCourseNotFound() {
        // Setup
        when(courseRepository.existsById(99L)).thenReturn(false);
        testCourse.setId(99L);

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.update(testCourse);
        });
    }

    @Test
    public void testDeleteCourse() {
        // Setup
        when(courseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(1L);

        // Execute
        courseService.delete(1L);

        // Verify
        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCourseNotFound() {
        // Setup
        when(courseRepository.existsById(99L)).thenReturn(false);

        // Execute and Verify
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.delete(99L);
        });
    }

    @Test
    public void testGetWithStudents() {
        // Setup
        Set<Student> students = new HashSet<>();
        students.add(testStudent);
        testCourse.setStudents(students);
        
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));

        // Execute
        Course result = courseService.getWithStudents(1L);

        // Verify
        assertNotNull(result);
        assertEquals(1, result.getStudents().size());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCoursesByStudentId() {
        // Setup
        List<Course> courses = Collections.singletonList(testCourse);
        when(courseRepository.findCoursesByStudentId(1L)).thenReturn(courses);

        // Execute
        List<Course> result = courseService.getCoursesByStudentId(1L);

        // Verify
        assertEquals(1, result.size());
        assertEquals("Test Course", result.get(0).getTitle());
        verify(courseRepository, times(1)).findCoursesByStudentId(1L);
    }

    @Test
    public void testGetCoursesWithStudentCount() {
        // Setup
        Object[] data = new Object[]{1L, "Test Course", 5L};
        List<Object[]> mockData = Collections.singletonList(data);
        
        when(courseRepository.countStudentsPerCourse()).thenReturn(mockData);

        // Execute
        List<Map<String, Object>> result = courseService.getCoursesWithStudentCount();

        // Verify
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).get("courseId"));
        assertEquals("Test Course", result.get(0).get("courseTitle"));
        assertEquals(5L, result.get(0).get("studentCount"));
        verify(courseRepository, times(1)).countStudentsPerCourse();
    }

    @Test
    public void testGetCoursesWithNoStudents() {
        // Setup
        List<Course> emptyCourses = Collections.singletonList(testCourse);
        when(courseRepository.findCoursesWithNoStudents()).thenReturn(emptyCourses);

        // Execute
        List<Course> result = courseService.getCoursesWithNoStudents();

        // Verify
        assertEquals(1, result.size());
        assertEquals("Test Course", result.get(0).getTitle());
        verify(courseRepository, times(1)).findCoursesWithNoStudents();
    }
}
