package com.example.studentcourse.controller;

import com.example.studentcourse.model.Course;
import com.example.studentcourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    // Display all courses
    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }
    
    // Display form to add a new course
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "addCourse";
    }
    
    // Process the form to add a new course
    @PostMapping("/add")
    public String addCourse(@Valid @ModelAttribute("course") Course course, 
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "addCourse";
        }
        
        try {
            courseService.save(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course added successfully!");
            return "redirect:/courses";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Course title already exists");
            return "addCourse";
        }
    }
    
    // Display form to update an existing course
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getById(id);
        model.addAttribute("course", course);
        return "updateCourse";
    }
    
    // Process the form to update a course
    @PostMapping("/update")
    public String updateCourse(@Valid @ModelAttribute("course") Course course,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "updateCourse";
        }
        
        try {
            courseService.update(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
            return "redirect:/courses";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Course title already exists");
            return "updateCourse";
        }
    }
    
    // Delete a course
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/courses";
    }
    
    // View a course's details with its students
    @GetMapping("/view/{id}")
    public String viewCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getWithStudents(id);
        model.addAttribute("course", course);
        return "viewCourse";
    }
    
    // View courses with student statistics
    @GetMapping("/stats")
    public String viewCourseStats(Model model) {
        List<Map<String, Object>> courseStats = courseService.getCoursesWithStudentCount();
        model.addAttribute("courseStats", courseStats);
        return "courseStats";
    }
}
