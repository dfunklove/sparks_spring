package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.Goal;

import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Long> {

  List<Goal> findAll();

  Goal findById(long id);
}