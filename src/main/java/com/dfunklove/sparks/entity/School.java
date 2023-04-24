package com.dfunklove.sparks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "schools")
public class School {

  @Id
	@GeneratedValue(generator = "school_generator")
	@SequenceGenerator(name="school_generator", sequenceName = "schools_id_seq", allocationSize = 1)
	private int id;

  @Column(nullable = false)
	private String name;

	protected School() {}

	public School(int id) {
		this.id = id;
	}

	public School(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("School[id=%d, name=%s]", id, name);
	}
}
