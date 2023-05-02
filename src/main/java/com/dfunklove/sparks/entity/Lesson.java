package com.dfunklove.sparks.entity;

import java.time.temporal.ChronoUnit;
import java.util.Set;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class Lesson implements Comparable {
	@Id
	@GeneratedValue(generator = "lesson_generator")
	@SequenceGenerator(name="lesson_generator", sequenceName = "lessons_id_seq", allocationSize = 1)
	private int id;

  @Column
	private String notes;

	@OneToMany
	@JoinColumn(name="lesson_id")
	@EqualsAndHashCode.Exclude
	private Set<Rating> ratings; 

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="group_lesson_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private GroupLesson groupLesson;

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

	public Lesson(int id) {
		this.id = id;
	}

	public Lesson(GroupLesson groupLesson, int schoolId, int studentId, java.time.LocalDateTime timeIn, int userId) {
		this.groupLesson = groupLesson;
		this.school = new School(schoolId);
		this.student = new Student(studentId);
		this.timeIn = timeIn;
		this.userId = userId;
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Lesson) {
			Lesson lesson = (Lesson) obj;
			if (timeOut != null && lesson.getTimeOut() != null)
				return timeOut.compareTo(lesson.getTimeOut());
			else
				return timeIn.compareTo(lesson.getTimeIn());
		} else if (obj instanceof GroupLesson) {
			GroupLesson lesson = (GroupLesson) obj;
			if (timeOut != null && lesson.getTimeOut() != null)
				return timeOut.compareTo(lesson.getTimeOut());
			else
				return timeIn.compareTo(lesson.getTimeIn());
		} else {
			return 0;
		}
	}

	public long length() {
		if (timeOut == null) {
			return (long) 0;
		}
		return timeIn.until(timeOut, ChronoUnit.MINUTES);
	}
}
