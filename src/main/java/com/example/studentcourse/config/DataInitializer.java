package com.example.studentcourse.config;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.CourseRepository;
import com.example.studentcourse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create sample courses
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Mathematics", "Advanced calculus and algebra"));
        courses.add(new Course("Physics", "Study of matter, energy, and the interaction between them"));
        courses.add(new Course("Computer Science", "Study of computers and computing technologies"));
        courses.add(new Course("Biology", "Study of living organisms and their interactions"));
        courses.add(new Course("Chemistry", "Study of the composition, structure, and properties of substances"));
        courses.add(new Course("History", "Study of past events and human affairs"));
        courses.add(new Course("English Literature", "Study of literature written in the English language"));
        courses.add(new Course("Geography", "Study of places and the relationships between people and their environments"));
        courses.add(new Course("Economics", "Study of how societies use scarce resources"));
        courses.add(new Course("Psychology", "Scientific study of the mind and behavior"));
        
        // Save all courses
        courseRepository.saveAll(courses);
        
        // Create sample students
        List<Student> students = new ArrayList<>();
        students.add(new Student(null, "John Doe", "john.doe@example.com", null));
        students.add(new Student(null, "Jane Smith", "jane.smith@example.com", null));
        students.add(new Student(null, "Bob Johnson", "bob.johnson@example.com", null));
        students.add(new Student(null, "Alice Brown", "alice.brown@example.com", null));
        students.add(new Student(null, "Charlie Davis", "charlie.davis@example.com", null));
        students.add(new Student(null, "Diana Wilson", "diana.wilson@example.com", null));
        students.add(new Student(null, "Edward Miller", "edward.miller@example.com", null));
        students.add(new Student(null, "Fiona Taylor", "fiona.taylor@example.com", null));
        students.add(new Student(null, "George Anderson", "george.anderson@example.com", null));
        students.add(new Student(null, "Helen Thomas", "helen.thomas@example.com", null));
        
        // Save all students
        studentRepository.saveAll(students);
        
        // Enroll students in courses (each student takes 2-4 courses)
        Student student1 = students.get(0);
        student1.addCourse(courses.get(0)); // Mathematics
        student1.addCourse(courses.get(1)); // Physics
        student1.addCourse(courses.get(2)); // Computer Science
        studentRepository.save(student1);
        
        Student student2 = students.get(1);
        student2.addCourse(courses.get(0)); // Mathematics
        student2.addCourse(courses.get(2)); // Computer Science
        student2.addCourse(courses.get(4)); // Chemistry
        studentRepository.save(student2);
        
        Student student3 = students.get(2);
        student3.addCourse(courses.get(1)); // Physics
        student3.addCourse(courses.get(3)); // Biology
        studentRepository.save(student3);
        
        Student student4 = students.get(3);
        student4.addCourse(courses.get(3)); // Biology
        student4.addCourse(courses.get(4)); // Chemistry
        student4.addCourse(courses.get(9)); // Psychology
        studentRepository.save(student4);
        
        Student student5 = students.get(4);
        student5.addCourse(courses.get(5)); // History
        student5.addCourse(courses.get(6)); // English Literature
        student5.addCourse(courses.get(7)); // Geography
        studentRepository.save(student5);
        
        Student student6 = students.get(5);
        student6.addCourse(courses.get(6)); // English Literature
        student6.addCourse(courses.get(8)); // Economics
        studentRepository.save(student6);
        
        Student student7 = students.get(6);
        student7.addCourse(courses.get(2)); // Computer Science
        student7.addCourse(courses.get(8)); // Economics
        student7.addCourse(courses.get(9)); // Psychology
        studentRepository.save(student7);
        
        Student student8 = students.get(7);
        student8.addCourse(courses.get(0)); // Mathematics
        student8.addCourse(courses.get(7)); // Geography
        student8.addCourse(courses.get(8)); // Economics
        studentRepository.save(student8);
        
        Student student9 = students.get(8);
        student9.addCourse(courses.get(1)); // Physics
        student9.addCourse(courses.get(5)); // History
        studentRepository.save(student9);
        
        Student student10 = students.get(9);
        student10.addCourse(courses.get(6)); // English Literature
        student10.addCourse(courses.get(9)); // Psychology
        studentRepository.save(student10);
        
        System.out.println("Data initialization completed!");
        System.out.println("Created " + courseRepository.count() + " courses");
        System.out.println("Created " + studentRepository.count() + " students");
    }
}
