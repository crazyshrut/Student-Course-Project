package com.example.studentcourse.controller;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.service.CourseService;
import com.example.studentcourse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    // Display all students
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.getAll();
        model.addAttribute("students", students);
        return "listStudents";
    }
    
    // Display form to add a new student
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAll());
        return "addStudent";
    }
    
    // Process the form to add a new student
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student, 
                             BindingResult result,
                             @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAll());
            return "addStudent";
        }
        
        try {
            Student savedStudent = studentService.save(student);
            
            // Register student to selected courses
            if (courseIds != null) {
                for (Long courseId : courseIds) {
                    studentService.registerStudentToCourse(savedStudent.getId(), courseId);
                }
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
            return "redirect:/students";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Email already exists");
            model.addAttribute("courses", courseService.getAll());
            return "addStudent";
        }
    }
    
    // Display form to update an existing student
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getWithCourses(id);
        List<Course> allCourses = courseService.getAll();
        
        model.addAttribute("student", student);
        model.addAttribute("courses", allCourses);
        return "updateStudent";
    }
    
    // Process the form to update a student
    @PostMapping("/update")
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult result,
                                @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAll());
            return "updateStudent";
        }
        
        try {
            // First remove all existing course associations
            Student existingStudent = studentService.getWithCourses(student.getId());
            for (Course course : existingStudent.getCourses()) {
                studentService.removeStudentFromCourse(student.getId(), course.getId());
            }
            
            // Update the student
            studentService.update(student);
            
            // Add new course associations
            if (courseIds != null) {
                for (Long courseId : courseIds) {
                    studentService.registerStudentToCourse(student.getId(), courseId);
                }
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
            return "redirect:/students";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Email already exists");
            model.addAttribute("courses", courseService.getAll());
            return "updateStudent";
        }
    }
    
    // Delete a student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/students";
    }
    
    // View a student's details with their courses
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getWithCourses(id);
        model.addAttribute("student", student);
        return "viewStudent";
    }
}
