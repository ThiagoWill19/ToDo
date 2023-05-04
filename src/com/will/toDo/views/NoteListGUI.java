package com.will.toDo.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.will.toDo.entities.Note;
import com.will.toDo.services.NoteService;

public class NoteListGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton createButtom, removeButtom;
	private NoteService noteService;
	private JList<Note> noteList;
	private DefaultListModel<Note> noteModel;

	public NoteListGUI() {
		super("Note List");


		noteService = new NoteService();
		noteModel =  new DefaultListModel<>();
		noteList = new JList<>(noteModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.setCellRenderer(new TaskListRenderer());


        noteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Note note = noteList.getSelectedValue();
                    if (note != null) {
                        NoteGUI noteGUI = new NoteGUI(note);
                        noteGUI.setLocationRelativeTo(getFocusOwner());


                    }
                }
            }
        });

        createButtom = new JButton("New Note");
        createButtom.addActionListener(new CreateButtomListener());

        removeButtom = new JButton("Remove");
        removeButtom.addActionListener(new RemoveButtomListener());

        JPanel bottomPanel =  new JPanel();
        bottomPanel.add(createButtom);
        bottomPanel.add(removeButtom);

        setLayout(new BorderLayout());

        add(new JScrollPane(noteList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        loadNotes();

        new Time().start();
	}


	private void loadNotes() {

		//Fetch the currently selected item from the list to keep it selected after items are updated
		Note n = noteList.getSelectedValue();

		noteModel.removeAllElements();
        List<Note> notes = noteService.getAll();
        for (Note note : notes) {
            noteModel.addElement(note);
        }

        //Keeps the item that was selected before the update
        noteList.setSelectedValue(n, rootPaneCheckingEnabled);

    }


	private class CreateButtomListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			new NoteGUI(null).setLocationRelativeTo(getFocusOwner());
		}

	}

	private class RemoveButtomListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			 Note note = noteList.getSelectedValue();
	            if (note != null) {
	                noteService.remove(note);
	                noteModel.removeElement(note);
	            }
		}

	}

	private class TaskListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Note note = (Note) value;
            label.setText(note.getTitle());
            return label;
        }
    }

	//Keeps the list up to date
	private class Time extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					sleep(2000);
					loadNotes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

}
