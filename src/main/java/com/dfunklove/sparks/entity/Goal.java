package com.dfunklove.sparks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goals")
public class Goal {

  @Id
	@GeneratedValue(generator = "goal_generator")
	@SequenceGenerator(name="goal_generator", sequenceName = "goals_id_seq", allocationSize = 1)
	private int id;

  @Column(nullable = false)
	private String name;

	public Goal() {}

	public Goal(int id) {
		this.id = id;
	}

	public Goal(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("Goal[id=%d, name=%s]", id, name);
	}
}
