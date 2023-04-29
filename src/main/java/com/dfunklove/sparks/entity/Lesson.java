package com.dfunklove.sparks.entity;

import java.time.temporal.ChronoUnit;
import java.util.Set;

import jakarta.persistence.*;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="group_lesson_id")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public GroupLesson getGroupLesson() {
		return groupLesson;
	}

	public void setGroupLesson(GroupLesson groupLesson) {
		this.groupLesson = groupLesson;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public java.time.LocalDateTime getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(java.time.LocalDateTime timeIn) {
		this.timeIn = timeIn;
	}

	public java.time.LocalDateTime getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(java.time.LocalDateTime timeOut) {
		this.timeOut = timeOut;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public long length() {
		if (timeOut == null) {
			return (long) 0;
		}
		return timeIn.until(timeOut, ChronoUnit.MINUTES);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result + ((timeIn == null) ? 0 : timeIn.hashCode());
		result = prime * result + ((timeOut == null) ? 0 : timeOut.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lesson other = (Lesson) obj;
		if (id != other.id)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (groupLesson == null) {
			if (other.groupLesson != null)
				return false;
		} else if (!groupLesson.equals(other.groupLesson))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (timeIn == null) {
			if (other.timeIn != null)
				return false;
		} else if (!timeIn.equals(other.timeIn))
			return false;
		if (timeOut == null) {
			if (other.timeOut != null)
				return false;
		} else if (!timeOut.equals(other.timeOut))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lesson [id=" + id + ", notes=" + notes + ", school=" + school + ", student=" + student + ", timeIn="
				+ timeIn + ", timeOut=" + timeOut + ", userId=" + userId + ", groupLessonId=" + (groupLesson != null ? groupLesson.getId() : 0) + "]";
	}

}
