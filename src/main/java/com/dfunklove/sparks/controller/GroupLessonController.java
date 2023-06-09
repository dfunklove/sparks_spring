package com.dfunklove.sparks.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.dfunklove.sparks.entity.GroupLesson;
import com.dfunklove.sparks.entity.Lesson;
import com.dfunklove.sparks.entity.Rating;
import com.dfunklove.sparks.entity.School;
import com.dfunklove.sparks.entity.Student;
import com.dfunklove.sparks.repository.GoalRepository;
import com.dfunklove.sparks.repository.GroupLessonRepository;
import com.dfunklove.sparks.repository.LessonRepository;
import com.dfunklove.sparks.repository.RatingRepository;
import com.dfunklove.sparks.repository.StudentRepository;

@Controller
public class GroupLessonController {

  @Autowired
  private GoalRepository goalRepo;
  @Autowired
  private GroupLessonRepository groupLessonRepo;
  @Autowired
  private LessonRepository lessonRepo;
  @Autowired
  private RatingRepository ratingRepo;
  @Autowired
  private StudentRepository studentRepo;

  @GetMapping("/group_lessons/new")
  public String addGroupLesson(Model model, RedirectAttributes redirectAttributes) {
    String url = SparksApplication.handleOpenLesson(groupLessonRepo, lessonRepo);
    if (url != null) {
      redirectAttributes.addFlashAttribute("message", "Please finish open lesson before starting a new one");
      return url;
    } else {
      List<Student> students = new ArrayList<Student>();
      model.addAttribute("students", students);
      studentRepo.findAll().forEach(students::add);
      return "group_lessons_new";
    }
  }

  @GetMapping("/group_lessons/{id}/checkout")
  public String checkout(Model model, @PathVariable(value="id") int id) {
    GroupLesson groupLesson = groupLessonRepo.findById(id);
    Map<Integer, List<Goal>> studentGoals = new HashMap<Integer, List<Goal>>(); //groupLesson.getStudent().getGoals());
    for (Lesson lesson : groupLesson.getLessons()) {
      List<Goal> sg = new ArrayList<Goal>(lesson.getStudent().getGoals());
      while (sg.size() < SparksApplication.MAX_GOALS_PER_STUDENT) {
        sg.add(new Goal());
      }
      studentGoals.put(lesson.getStudent().getId(), sg);
    }
    List<Goal> goals = goalRepo.findAll();
    int[] ratingScale = new int[]{10,9,8,7,6,5,4,3,2,1};
    model.addAttribute("goals", goals);
    model.addAttribute("groupLesson", groupLesson);
    model.addAttribute("ratingScale", ratingScale);
    model.addAttribute("studentGoals", studentGoals);
    return "group_lessons_checkout";
  }

  @PostMapping(value="/group_lessons", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String saveGroupLesson(RedirectAttributes redirectAttributes, @RequestBody MultiValueMap<String, String> formData) {
    try {
      int studentCount = Integer.parseInt(formData.getFirst("student_count"));
      int studentIds[] = new int[studentCount];
      int schoolId = 0;
      for (int i=0; i < studentCount; i++) {
        String selected = formData.getFirst("student_"+i+"_selected");
        if (selected != null && selected.trim().length() > 0) {
          String id = formData.getFirst("student_"+i+"_id");
          studentIds[i] = Integer.parseInt(id);
          schoolId = Integer.parseInt(formData.getFirst("student_"+i+"_school_id"));
        }
      }
      java.time.LocalDateTime timeIn = java.time.LocalDateTime.now();
      GroupLesson groupLesson = new GroupLesson(schoolId, timeIn, 1);

      Set<Lesson> lessons = new HashSet<Lesson>(studentIds.length);
      for (int studentId : studentIds) {
        lessons.add(new Lesson(groupLesson, schoolId, studentId, timeIn, 1));
      }  
      groupLesson.setLessons(lessons);
      groupLessonRepo.save(groupLesson);

      //redirectAttributes.addFlashAttribute("message", "The Group Lesson has been saved successfully!");
      return "redirect:/group_lessons/"+groupLesson.getId()+"/checkout";
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
      return "redirect:/group_lessons/new";
    }
  }

  @PostMapping(value="/group_lessons/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String updateGroupLesson(RedirectAttributes redirectAttributes, @PathVariable(value="id") int id,
      @RequestBody MultiValueMap<String, String> formData) {
    try {
      GroupLesson groupLesson = groupLessonRepo.findById(id);
      java.time.LocalDateTime timeOut = LocalDateTime.now();
      groupLesson.setTimeOut(timeOut);
      groupLesson.setNotes(formData.getFirst("notes"));

      Map<Integer, Lesson> lessons = new LinkedHashMap<Integer, Lesson>(groupLesson.getLessons().size());
      for (Lesson lesson : groupLesson.getLessons()) {
        lessons.put(lesson.getId(), lesson);
      }

      int studentCount = Integer.parseInt(formData.getFirst("student_count"));
      for (int s=0; s < studentCount; s++) {
        String prefix = "student_"+s+"_";
        int lessonId = Integer.parseInt(formData.getFirst(prefix+"id"));
        Lesson lesson = lessons.get(lessonId);
        Set<Goal> newGoals = new HashSet<Goal>(SparksApplication.MAX_GOALS_PER_STUDENT);
        Set<Rating> ratings = new HashSet<Rating>(SparksApplication.MAX_GOALS_PER_STUDENT);
        for (int r=0; r < SparksApplication.MAX_GOALS_PER_STUDENT; r++) {
          String goalId = formData.getFirst(prefix+"rating"+r+"_goalId");
          if (goalId != null && goalId.trim().length() > 0) {
            int goalIdInt = Integer.parseInt(goalId);
            int score = Integer.parseInt(formData.getFirst(prefix+"rating"+r+"_score"));
            ratings.add(new Rating(lesson.getId(), goalIdInt, score));
            newGoals.add(new Goal(goalIdInt));
          }
        }
        lesson.setTimeOut(timeOut);
        lesson.setNotes(formData.getFirst(prefix+"notes"));
        lesson.getStudent().setGoals(newGoals);
        ratingRepo.saveAll(ratings);
      }
      groupLessonRepo.save(groupLesson);

      //redirectAttributes.addFlashAttribute("message", "The GroupLesson has been saved successfully!");
      return "redirect:/group_lessons/new";
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
      return "redirect:/group_lessons/"+id+"/checkout";
    }
  }
}