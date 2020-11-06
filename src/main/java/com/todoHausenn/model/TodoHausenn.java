package com.todoHausenn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.todoHausenn.model.generic.BaseEntity;

@Entity
@Table(name = "TODOHAUSENN")
public class TodoHausenn extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	@NotEmpty
	@Size(min = 3)
	private String name;

	@Column(name = "DONE")
	private Boolean done;

	public TodoHausenn() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "TodoHausenn [name=" + name + ", done=" + done + "]";
	}

	

	

	

}
