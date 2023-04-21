package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.School;

import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<School, Long> {

  List<School> findAll();

  School findById(long id);
}