package com.example.studentcourse.service;

import com.example.studentcourse.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    
    // Basic CRUD operations
    Course save(Course course);
    
    List<Course> getAll();
    
    Course getById(Long id);
    
    Course update(Course course);
    
    void delete(Long id);
    
    // Custom operations
    Course getWithStudents(Long id);
    
    List<Course> getCoursesByStudentId(Long studentId);
    
    List<Map<String, Object>> getCoursesWithStudentCount();
    
    List<Course> getCoursesWithNoStudents();
}
