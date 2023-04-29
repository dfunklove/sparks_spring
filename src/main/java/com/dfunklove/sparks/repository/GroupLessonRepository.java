package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.GroupLesson;

import org.springframework.data.repository.CrudRepository;

public interface GroupLessonRepository extends CrudRepository<GroupLesson, Long> {

  List<GroupLesson> findAll();

  GroupLesson findById(long id);
}