package com.dfunklove.sparks.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {

  @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

  @Column(nullable = false)
	private String notes;

	protected Lesson() {}

	public Lesson(String notes) {
		this.notes = notes;
	}

	@OneToMany
	@JoinColumn(name="lesson_id")
	private Set<Rating> ratings; 

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  private School school;

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

	public String toString() {
		return String.format("Lesson[id=%d, notes=%s]", id, notes);
	}
}
