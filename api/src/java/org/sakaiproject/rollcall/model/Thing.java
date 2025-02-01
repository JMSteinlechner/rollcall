package org.sakaiproject.rollcall.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PROJECT_THINGS")
public class Thing implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "TITLE", nullable = false)
	private String name;

	// Constructors
	public Thing() {}

	public Thing(String name) {
		this.name = name;
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Thing{id=" + id + ", name='" + name + "'}";
	}
}
