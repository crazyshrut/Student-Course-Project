package com.example.studentcourse.service;

import com.example.studentcourse.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    
    // Basic CRUD operations
    Student save(Student student);
    
    List<Student> getAll();
    
    Student getById(Long id);
    
    Student update(Student student);
    
    void delete(Long id);
    
    // Custom operations
    Student getWithCourses(Long id);
    
    List<Map<String, Object>> getStudentCourseData();
    
    List<Student> getStudentsByCourseId(Long courseId);
    
    void registerStudentToCourse(Long studentId, Long courseId);
    
    void removeStudentFromCourse(Long studentId, Long courseId);
}
