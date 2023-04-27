package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.Rating;

import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {

  List<Rating> findAll();

  Rating findById(long id);
}