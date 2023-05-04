package com.will.toDo.entities;

import java.io.Serializable;
import java.util.Objects;

public class Note implements Serializable{
	private static final long serialVersionUID = 1L;


	private int id;
	private String title;
	private String annotation;


	public Note(int id, String title, String annotation) {
		super();
		this.id = id;
		this.title = title;
		this.annotation = annotation;
	}

	public Note() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
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
		Note other = (Note) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", annotation=" + annotation + "]" ;
	}








}
