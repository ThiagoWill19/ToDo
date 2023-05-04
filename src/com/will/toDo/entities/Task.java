package com.will.toDo.entities;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String taskName;
	private boolean completed;

	public Task(int id, String taskName) {

		this.id = id;
		this.taskName = taskName;

	}


	public Task() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Task other = (Task) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", completed=" + completed + "]";
	}





}
