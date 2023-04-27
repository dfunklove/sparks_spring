package com.dfunklove.sparks.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dfunklove.sparks.SparksApplication;
import com.dfunklove.sparks.entity.Goal;
import com.dfunklove.sparks.entity.Lesson;
import com.dfunklove.sparks.entity.Rating;
import com.dfunklove.sparks.entity.School;
import com.dfunklove.sparks.entity.Student;
import com.dfunklove.sparks.repository.GoalRepository;
import com.dfunklove.sparks.repository.LessonRepository;
import com.dfunklove.sparks.repository.RatingRepository;
import com.dfunklove.sparks.repository.StudentRepository;

@Controller
public class LessonController {

  @Autowired
  private GoalRepository goalRepo;
  @Autowired
  private LessonRepository lessonRepo;
  @Autowired
  private RatingRepository ratingRepo;
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

    try {
      List<Student> students = new ArrayList<Student>();
      studentRepo.findAll().forEach(students::add);
      model.addAttribute("students", students);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "lessons_new";
  }

  @GetMapping("/lessons/{id}/checkout")
  public String checkout(Model model, @PathVariable(value="id") int id) {
    Lesson lesson = lessonRepo.findById(id);
    List<Goal> sg = new ArrayList<Goal>(lesson.getStudent().getGoals());
    while (sg.size() < SparksApplication.MAX_GOALS_PER_STUDENT) {
      sg.add(new Goal());
    }
    List<Goal> goals = goalRepo.findAll();
    int[] ratingScale = new int[]{10,9,8,7,6,5,4,3,2,1};
    model.addAttribute("goals", goals);
    model.addAttribute("lesson", lesson);
    model.addAttribute("ratingScale", ratingScale);
    model.addAttribute("studentGoals", sg);
    return "lessons_checkout";
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
      return "redirect:/lessons/"+lesson.getId()+"/checkout";
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
      return "redirect:/lessons/new";
    }
  }

  @PostMapping(value="/lessons/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String updateLesson(RedirectAttributes redirectAttributes, @PathVariable(value="id") int id,
      @RequestBody MultiValueMap<String, String> formData) {
    try {
      Lesson lesson = lessonRepo.findById(id);
      lesson.setTimeOut(LocalDateTime.now());
      lesson.setNotes(formData.getFirst("notes"));

      //TODO
      formData.entrySet().forEach((entry) -> System.out.println(entry.getKey()+" "+entry.getValue()));

      Set<Rating> ratings = lesson.getRatings();
      for (int i=0; i < SparksApplication.MAX_GOALS_PER_STUDENT; i++) {
        String goalId = formData.getFirst("rating"+i+"_goalId");
        if (goalId != null && goalId.trim().length() > 0) {
          int goalIdInt = Integer.parseInt(goalId);
          int score = Integer.parseInt(formData.getFirst("rating"+i+"_score"));
          ratings.add(new Rating(lesson.getId(), goalIdInt, score));
        }
      }
      ratingRepo.saveAll(lesson.getRatings());
      lessonRepo.save(lesson);

      redirectAttributes.addFlashAttribute("message", "The Lesson has been saved successfully!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/lessons/"+id+"/checkout";
  }
}