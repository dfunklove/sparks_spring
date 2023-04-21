package com.dfunklove.sparks.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
  @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

  @Column(nullable = false)
	private String firstName;

  @ManyToMany
  @JoinTable(name="student_goals",
    joinColumns = @JoinColumn(name="student_id"),
    inverseJoinColumns = @JoinColumn(name="goal_id"))
  private Set<Goal> goals = new HashSet<>();

  @Column(nullable = false)
	private String lastName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

  public Student() {}

  public Student(String firstName, String lastName, School school) {
		this.firstName = firstName;
    this.lastName = lastName;
    this.school = school;
	}  

  public String toString() {
		return String.format("Student[id=%d, name=%s %s, school=%s]", id, firstName, lastName, school);
	}
}
