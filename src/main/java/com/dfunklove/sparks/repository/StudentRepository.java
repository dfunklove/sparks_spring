package com.dfunklove.sparks.repository;

import java.util.List;
import com.dfunklove.sparks.entity.Student;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

  List<Student> findAll();

  Student findById(long id);
}