package com.example.studentcourse.repository;

import com.example.studentcourse.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    // Find course by title
    Course findByTitle(String title);
    
    // Find courses by student id
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = ?1")
    List<Course> findCoursesByStudentId(Long studentId);
    
    // Find courses with student count
    @Query("SELECT c.id, c.title, COUNT(s.id) FROM Course c JOIN c.students s GROUP BY c.id, c.title")
    List<Object[]> countStudentsPerCourse();
    
    // Find courses that have no students
    @Query("SELECT c FROM Course c WHERE c.students IS EMPTY")
    List<Course> findCoursesWithNoStudents();
}
