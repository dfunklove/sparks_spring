package com.dfunklove.sparks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ratings")
public class Rating {

  @Id
	@GeneratedValue(generator = "rating_generator")
	@SequenceGenerator(name="rating_generator", sequenceName = "ratings_id_seq", allocationSize = 1)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goal_id", nullable = false)
  private Goal goal;

	@Column(name="lesson_id")
	private int lessonId;

  @Column(nullable = false)
	private int score;

	public Rating() {}

	public Rating(int id) {
		this.id = id;
	}

	public Rating(int lessonId, int goalId, int score) {
		this.lessonId = lessonId;
		this.score = score;
		this.goal = new Goal(goalId);
	}
	
	public String toString() {
		return String.format("Rating[id=%d, score=%s]", id, score);
	}
}
