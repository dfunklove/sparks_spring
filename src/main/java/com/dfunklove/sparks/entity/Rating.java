package com.dfunklove.sparks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ratings")
public class Rating {

  @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goal_id", nullable = false)
  private Goal goal;

	private Long lesson_id;

  @Column(nullable = false)
	private int score;

	protected Rating() {}

	public Rating(int score) {
		this.score = score;
	}
	
	public String toString() {
		return String.format("Rating[id=%d, score=%s]", id, score);
	}
}
