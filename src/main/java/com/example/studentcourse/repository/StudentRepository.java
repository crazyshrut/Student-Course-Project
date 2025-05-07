package com.example.studentcourse.repository;

import com.example.studentcourse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find student by email (useful for validation)
    Student findByEmail(String email);
    
    // Custom query: List all students with their course names (inner join)
    @Query("SELECT s.name as studentName, c.title as courseTitle FROM Student s JOIN s.courses c")
    List<Object[]> fetchStudentCourseData();
    
    // Find students by course id
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = ?1")
    List<Student> findStudentsByCourseId(Long courseId);
    
    // Count number of courses per student
    @Query("SELECT s.id, s.name, COUNT(c.id) FROM Student s JOIN s.courses c GROUP BY s.id, s.name")
    List<Object[]> countCoursesPerStudent();
}
