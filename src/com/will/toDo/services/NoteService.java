package com.will.toDo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.will.toDo.entities.Note;

public class NoteService {

	private List<Note> notes;



	private void save() {

		try {

			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("note.ser"));
			output.writeObject(notes);
			output.close();

		} catch (Exception e) {
			e.getMessage();
		}

	}



	@SuppressWarnings("unchecked")
	private void load(){

		notes = new ArrayList<>();
		try {

			ObjectInputStream input = new ObjectInputStream(new FileInputStream("note.ser"));
			notes = (List<Note>) input.readObject();
			input.close();

		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}


	}



	public void add(Note note) {

		load();
		if(!notes.isEmpty()) {
			note.setId(notes.get(notes.size() -1).getId() + 1);
		}
		notes.add(note);
		save();

	}



	public void remove(Note note) {

		load();
		if(notes.contains(note)) {
			notes.remove(note);
		}
		save();
	}



	public List<Note> getAll(){
		load();
		return notes;
	}



	public Note getById(int id) {

		for(Note t : getAll()) {
			if(t.getId() == id) return t;
		}

		return null;
	}



	public void update(Note note) {

		load();
		for(int i = 0; i < notes.size(); i++) {
			if(notes.get(i).equals(note)) {
				notes.set(i, note);
			}
		}
		save();
	}

	public boolean exportFomTxt(String path, Note note) {

		String txt;

		if(note != null) {
			txt =  note.getTitle() + "\n";
			txt += note.getAnnotation();

			try {
				File file = new File(path +"/"+note.getTitle()+".txt");
				FileWriter writer = new FileWriter(file);
				writer.write(txt);
				writer.close();
				return true;

			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		return false;

	}

}
