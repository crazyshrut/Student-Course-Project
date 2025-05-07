package com.example.studentcourse.service;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.CourseRepository;
import com.example.studentcourse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Student save(Student student) {
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email already exists: " + student.getEmail());
        }
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public Student update(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new EntityNotFoundException("Student not found with ID: " + student.getId());
        }
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email already exists: " + student.getEmail());
        }
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Student getWithCourses(Long id) {
        Student student = getById(id);
        // Force initialization of the courses collection
        student.getCourses().size();
        return student;
    }

    @Override
    public List<Map<String, Object>> getStudentCourseData() {
        List<Object[]> results = studentRepository.fetchStudentCourseData();
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        
        for (Object[] result : results) {
            Map<String, Object> mappedResult = new HashMap<>();
            mappedResult.put("studentName", result[0]);
            mappedResult.put("courseTitle", result[1]);
            mappedResults.add(mappedResult);
        }
        
        return mappedResults;
    }

    @Override
    public List<Student> getStudentsByCourseId(Long courseId) {
        return studentRepository.findStudentsByCourseId(courseId);
    }

    @Override
    @Transactional
    public void registerStudentToCourse(Long studentId, Long courseId) {
        Student student = getById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
        
        student.addCourse(course);
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = getById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
        
        student.removeCourse(course);
        studentRepository.save(student);
    }
}
