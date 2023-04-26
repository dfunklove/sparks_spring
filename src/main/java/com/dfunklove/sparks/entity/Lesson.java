package com.dfunklove.sparks.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {

  @Id
	@GeneratedValue(generator = "lesson_generator")
	@SequenceGenerator(name="lesson_generator", sequenceName = "lessons_id_seq", allocationSize = 1)
	private int id;

  @Column
	private String notes;

	@OneToMany
	@JoinColumn(name="lesson_id")
	private Set<Rating> ratings; 

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

	@Column(name="time_in", nullable = false)
	private java.time.LocalDateTime timeIn;

	@Column(name="time_out")
	private java.time.LocalDateTime timeOut;

	@Column(name="user_id", nullable = false)
	private int userId;

	public Lesson() {}

	public Lesson(String notes) {
		this.notes = notes;
	}

	public String toString() {
		return String.format("Lesson[id=%d, notes=%s]", id, notes);
	}
}
