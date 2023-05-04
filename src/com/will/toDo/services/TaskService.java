package com.will.toDo.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.will.toDo.entities.Task;

public class TaskService {

	private List<Task> tasks;



	private void save() {

		try {

			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("tasks.ser"));
			output.writeObject(tasks);
			output.close();

		} catch (Exception e) {
			e.getMessage();
		}

	}



	@SuppressWarnings("unchecked")
	private void load(){

		tasks = new ArrayList<>();
		try {

			ObjectInputStream input = new ObjectInputStream(new FileInputStream("tasks.ser"));
			tasks = (List<Task>) input.readObject();
			input.close();

		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}


	}



	public void add(Task task) {

		load();
		if(!tasks.isEmpty()) {
			task.setId(tasks.get(tasks.size() -1).getId() + 1);
		}
		tasks.add(task);
		save();

	}



	public void remove(Task task) {

		load();
		if(tasks.contains(task)) {
			tasks.remove(task);
		}
		save();
	}



	public List<Task> getAll(){
		load();
		return tasks;
	}



	public Task getById(int id) {

		for(Task t : getAll()) {
			if(t.getId() == id) return t;
		}

		return null;
	}



	public void update(Task task) {

		load();
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).equals(task)) {
				tasks.set(i, task);
			}
		}
		save();
	}



}
