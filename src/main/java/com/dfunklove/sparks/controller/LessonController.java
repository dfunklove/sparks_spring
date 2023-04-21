package com.dfunklove.sparks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dfunklove.sparks.entity.Lesson;
import com.dfunklove.sparks.entity.Rating;
import com.dfunklove.sparks.repository.LessonRepository;

@Controller
public class LessonController {

  @Autowired
  private LessonRepository lessonRepository;

  @GetMapping("/lessons")
  public String getAll(Model model) {

    try {
      List<Lesson> lessons = new ArrayList<Lesson>();
      lessonRepository.findAll().forEach(lessons::add);
      model.addAttribute("lessons", lessons);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    
    return "lessons";
  }
}