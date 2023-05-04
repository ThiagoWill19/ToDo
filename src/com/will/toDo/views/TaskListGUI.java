package com.will.toDo.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.will.toDo.entities.Task;
import com.will.toDo.services.TaskService;

public class TaskListGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemNotes;

	private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    private JButton addButton, removeButton, completeButton;
    private JTextField taskNameField;
    private TaskService taskService;
    private JLabel status = new JLabel();

    public TaskListGUI() {
        super("Task List");

        setResizable(false);

        menuItemNotes = new JMenuItem("Notes");
        menuItemNotes.addActionListener(new NotesListener());
        menu =  new JMenu("More");
        menuBar = new JMenuBar();

        menu.add(menuItemNotes);
        menuBar.add(menu);


        taskService = new TaskService();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskListRenderer());

        addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveButtonListener());

        completeButton = new JButton("Complete/unmark");
        completeButton.addActionListener(new CompleteButtonListener());

        taskNameField = new JTextField(20);

        JPanel topPanel = new JPanel();
        topPanel.add(taskNameField);
        topPanel.add(addButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeButton);
        bottomPanel.add(completeButton);
        bottomPanel.add(status);

        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = taskService.getAll();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
        attStatus();

    }

    public void attStatus() {

    	int total = taskService.getAll().size();
    	int comp = 0;

    	for(Task t : taskService.getAll()) {
    		if(t.isCompleted()) {
    			comp ++;
    		}
    	}

    	status.setText(comp + " / " + total);
    	if(comp == total && total > 0) {
    		status.setForeground(Color.GREEN);
    	}else {
    		status.setForeground(Color.black);
    	}
    }


    private class NotesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			NoteListGUI noteList =  new NoteListGUI();
			noteList.setLocationRelativeTo(getFocusOwner());
			noteList.setVisible(true);
		}

    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskName = taskNameField.getText().trim();
            if (!taskName.isEmpty()) {
                Task task = new Task(0, taskName);
                taskService.add(task);
                listModel.addElement(task);
                taskNameField.setText("");
                attStatus();
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                taskService.remove(task);
                listModel.removeElement(task);
            }
            attStatus();
        }
    }

    private class CompleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                task.setCompleted(!task.isCompleted());

                if(task.isCompleted()) {
                	Toolkit.getDefaultToolkit().beep();
                }
                taskService.update(task);
                taskList.repaint();
                attStatus();
            }
        }
    }

    private class TaskListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Task task = (Task) value;
            label.setText(task.getTaskName());
            if (task.isCompleted()) {
                label.setForeground(Color.green);
                label.setOpaque(false);
            } else {
                label.setForeground(Color.BLACK);
                label.setOpaque(false);
            }

            return label;
        }
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new TaskListGUI().setVisible(true));
    }

}
