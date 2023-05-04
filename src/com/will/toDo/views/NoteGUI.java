package com.will.toDo.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.will.toDo.entities.Note;
import com.will.toDo.services.NoteService;

public class NoteGUI extends JFrame{
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem itemMenuExport;

	private JLabel lTitle;
	private JTextField title;
	private JTextArea annotation;

	private JButton save;
	private JButton cancel;

	private NoteService noteService;
	private Note n;

	Date date;

	public NoteGUI(Note note) {
		super("Note");

		this.n = note;

		if(n != null) {
			menuBar = new JMenuBar();
			menu = new JMenu("Files");
			itemMenuExport = new JMenuItem("Export");

			itemMenuExport.addActionListener(new ExportListener());
			menu.add(itemMenuExport);
			menuBar.add(menu);

			setJMenuBar(menuBar);
		}


		noteService = new NoteService();
		lTitle = new JLabel("Title");
		title = new JTextField(20);
		annotation = new JTextArea(10,20);

		if(n != null) {
			title.setText(n.getTitle());
			annotation.setText(n.getAnnotation());
		}

		JPanel topPanel = new JPanel();
		topPanel.add(lTitle);
		topPanel.add(title);


		save = new JButton("Save");
		save.addActionListener(new SaveAction());

		cancel = new JButton("Cancel/Back");
		cancel.addActionListener(new CancelAction());

		JPanel bottomPanel =  new JPanel();
		bottomPanel.add(save);
		bottomPanel.add(cancel);



		setLayout(new BorderLayout());

		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(annotation), BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);


		pack();
		setResizable(false);
		setVisible(true);

	}



	private class SaveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Note note = new Note();

			if(!title.getText().isEmpty() && !annotation.getText().isEmpty()) {

				note.setTitle(title.getText());
				note.setAnnotation(annotation.getText());

				if(n != null) {
					note.setId(n.getId());
					noteService.update(note);
				}else {
					noteService.add(note);
				}

				dispose();

			}else
				JOptionPane.showMessageDialog(rootPane, "fill in the fields!");
			}

		}



	private class CancelAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			dispose();

		}

	}

	private class ExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose a folder");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int result = chooser.showOpenDialog(rootPane);

			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFolder = chooser.getSelectedFile();
			    if(noteService.exportFomTxt(selectedFolder.toString(), n)) {
			    	JOptionPane.showMessageDialog(rootPane, "Saved file");
			    } else {
			    	JOptionPane.showMessageDialog(rootPane, "Error saving file");
			    }

			} else {
			   JOptionPane.showMessageDialog(rootPane, "No folder selected");
			}

		}

	}

}
