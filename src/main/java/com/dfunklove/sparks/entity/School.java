package com.dfunklove.sparks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "schools")
public class School {

  @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

  @Column(nullable = false)
	private String name;

	protected School() {}

	public School(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("School[id=%d, name=%s]", id, name);
	}
}
