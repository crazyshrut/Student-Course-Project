package com.example.studentcourse.controller;

import com.example.studentcourse.service.CourseService;
import com.example.studentcourse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("studentCount", studentService.getAll().size());
        model.addAttribute("courseCount", courseService.getAll().size());
        model.addAttribute("studentCourseData", studentService.getStudentCourseData());
        return "home";
    }
}
