package com.dfunklove.sparks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goals")
public class Goal {

  @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

  @Column(nullable = false)
	private String name;

	protected Goal() {}

	public Goal(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("Goal[id=%d, name=%s]", id, name);
	}
}
