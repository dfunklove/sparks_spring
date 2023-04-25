package com.dfunklove.sparks.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dfunklove.sparks.entity.Lesson;
import com.dfunklove.sparks.entity.Rating;
import com.dfunklove.sparks.entity.School;
import com.dfunklove.sparks.entity.Student;
import com.dfunklove.sparks.repository.LessonRepository;
import com.dfunklove.sparks.repository.StudentRepository;

@Controller
public class LessonController {

  @Autowired
  private LessonRepository lessonRepo;
  @Autowired
  private StudentRepository studentRepo;


  @GetMapping("/lessons")
  public String getAll(Model model) {

    try {
      List<Lesson> lessons = new ArrayList<Lesson>();
      lessonRepo.findAll().forEach(lessons::add);
      model.addAttribute("lessons", lessons);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    
    return "lessons";
  }

  @GetMapping("/lessons/new")
  public String addLesson(Model model) {
    Lesson lesson = new Lesson();

    model.addAttribute("lesson", lesson);
    model.addAttribute("pageTitle", "Create new Lesson");

    try {
      List<Student> students = new ArrayList<Student>();
      studentRepo.findAll().forEach(students::add);
      model.addAttribute("students", students);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "lessons_new";
  }

  @PostMapping("/lessons")
  public String saveLesson(Lesson lesson, RedirectAttributes redirectAttributes, @RequestParam int studentId, @RequestParam int schoolId) {
    try {
      lesson.setStudent(new Student(studentId));
      lesson.setSchool(new School(schoolId));
      lesson.setUserId(1);
      lesson.setTimeIn(LocalDateTime.now());
      lessonRepo.save(lesson);

      redirectAttributes.addFlashAttribute("message", "The Lesson has been saved successfully!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/lessons";
  }
}