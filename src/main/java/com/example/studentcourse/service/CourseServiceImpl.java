package com.example.studentcourse.service;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.repository.CourseRepository;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course course) {
        try {
            return courseRepository.save(course);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Course title already exists: " + course.getTitle());
        }
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id));
    }

    @Override
    public Course update(Course course) {
        if (!courseRepository.existsById(course.getId())) {
            throw new EntityNotFoundException("Course not found with ID: " + course.getId());
        }
        try {
            return courseRepository.save(course);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Course title already exists: " + course.getTitle());
        }
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with ID: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Course getWithStudents(Long id) {
        Course course = getById(id);
        // Force initialization of the students collection
        course.getStudents().size();
        return course;
    }

    @Override
    public List<Course> getCoursesByStudentId(Long studentId) {
        return courseRepository.findCoursesByStudentId(studentId);
    }

    @Override
    public List<Map<String, Object>> getCoursesWithStudentCount() {
        List<Object[]> results = courseRepository.countStudentsPerCourse();
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        
        for (Object[] result : results) {
            Map<String, Object> mappedResult = new HashMap<>();
            mappedResult.put("courseId", result[0]);
            mappedResult.put("courseTitle", result[1]);
            mappedResult.put("studentCount", result[2]);
            mappedResults.add(mappedResult);
        }
        
        return mappedResults;
    }

    @Override
    public List<Course> getCoursesWithNoStudents() {
        return courseRepository.findCoursesWithNoStudents();
    }
}
