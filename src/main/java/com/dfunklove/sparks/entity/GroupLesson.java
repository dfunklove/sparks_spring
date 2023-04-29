package com.dfunklove.sparks.entity;

import java.time.temporal.ChronoUnit;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

	@OneToMany(
		mappedBy = "groupLesson",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	@EqualsAndHashCode.Exclude
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
		this.school = new School(schoolId);
		this.timeIn = timeIn;
		this.userId = userId;
	}

	public long length() {
		if (timeOut == null) {
			return (long) 0;
		}
		return timeIn.until(timeOut, ChronoUnit.MINUTES);
	}
}
