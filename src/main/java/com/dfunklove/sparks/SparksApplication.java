package com.dfunklove.sparks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dfunklove.sparks.entity.GroupLesson;
import com.dfunklove.sparks.entity.Lesson;
import com.dfunklove.sparks.repository.GroupLessonRepository;
import com.dfunklove.sparks.repository.LessonRepository;

@SpringBootApplication
public class SparksApplication {
	public static final int MAX_GOALS_PER_STUDENT = 3;
	public static void main(String[] args) {
		SpringApplication.run(SparksApplication.class, args);
	}

	public static String handleOpenLesson(GroupLessonRepository groupLessonRepo, LessonRepository lessonRepo) {
		Lesson openLesson = lessonRepo.findFirstByGroupLessonIdIsNullAndTimeOutIsNull();
    GroupLesson openGroupLesson = null;
    if (openLesson == null)
      openGroupLesson = groupLessonRepo.findFirstByTimeOutIsNull();

    if (openLesson != null) {
      int id = openLesson.getId();
      return "redirect:/lessons/"+id+"/checkout";
		} else if (openGroupLesson != null) {
			int id = openGroupLesson.getId();
			return "redirect:/group_lessons/"+id+"/checkout";
		} else {
			return null;
		}
	}

}
