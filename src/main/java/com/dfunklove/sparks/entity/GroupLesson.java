package com.dfunklove.sparks.entity;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "group_lessons")
public class GroupLesson {

  @Id
	@GeneratedValue(generator = "group_lesson_generator")
	@SequenceGenerator(name="group_lesson_generator", sequenceName = "group_lessons_id_seq", allocationSize = 1)
	private int id;

  @Column
	private String notes;

	@OneToMany
	@JoinColumn(name="group_lesson_id")
	private Set<Lesson> lessons;

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

	@Column(name="time_in", nullable = false)
	private java.time.LocalDateTime timeIn;

	@Column(name="time_out")
	private java.time.LocalDateTime timeOut;

	@Column(name="user_id", nullable = false)
	private int userId;

	public GroupLesson() {}

	public GroupLesson(int schoolId, java.time.LocalDateTime timeIn, int userId) {
		setSchool(new School(schoolId));
		setTimeIn(timeIn);
		setUserId(userId);
	}

	public long length() {
		if (timeOut == null) {
			return (long) 0;
		}
		return timeIn.until(timeOut, ChronoUnit.MINUTES);
	}

	public String toString() {
		return String.format("GroupLesson[id=%d, notes=%s]", id, notes);
	}
}
