package com.dfunklove.sparks.entity;

import java.time.temporal.ChronoUnit;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((school == null) ? 0 : school.hashCode());
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
		GroupLesson other = (GroupLesson) obj;
		if (id != other.id)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
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
		return "GroupLesson [id=" + id + ", notes=" + notes + ", school=" + school + ", timeIn=" + timeIn + ", timeOut="
				+ timeOut + ", userId=" + userId + "]";
	}
}
