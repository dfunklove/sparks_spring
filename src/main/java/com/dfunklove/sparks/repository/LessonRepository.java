package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.Lesson;

import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

  List<Lesson> findAll();

  List<Lesson> findByGroupLessonIdIsNullOrderByTimeOutDesc();

  Lesson findById(long id);
}