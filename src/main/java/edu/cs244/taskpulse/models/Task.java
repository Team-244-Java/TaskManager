package edu.cs244.taskpulse.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class Task {
	    private int taskId;
	    private String title;
	    private String description;
	    private String dueDate;
	    private String status;
	    private int userId;
	    private String priority;
//	    add a list Task
	    private List<Task> tasks;
	    
	    //Constructor : setting the defaults...
	    public Task(int taskId, String title, String description, String dueDate, String status, int userId, String priority) {
	        this.taskId = taskId;
	        this.title = title;
	        this.description = description;
	        this.dueDate = dueDate;
	        this.status = status;
	        this.userId = userId;
	        this.priority = priority;
	        this.tasks = new ArrayList<>();
	    }
	    
	    public Task(String title) {
	    	this.title=title;
	    }

	    public int getTaskId() {
	        return taskId;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public String getDueDate() {
	        return dueDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public String getPriority() {
	        return priority;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public void setDueDate(String dueDate) {
	        this.dueDate = dueDate;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public void setPriority(String priority) {
	        this.priority = priority;
	    }

	    public void addTask(Task task) {
	    	tasks.add(task);
	    }

	    public void updateTask(Task updatedTask) {
	    	try {
	    		for(int i = 0; i < tasks.size(); i++) {
	    			if(tasks.get(i).getTaskId() == updatedTask.getTaskId()) {
	    				tasks.set(i, updatedTask);
	    				return;
	    			}
	    		}
	    		throw new Exception("Task with taskId " + updatedTask.getTaskId() + " not found.");
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }

	    public void deleteTask(int taskId) {
	    	try {
	    		Iterator<Task> iterator = tasks.iterator();
	    		while(iterator.hasNext()) {
	    			Task task = iterator.next();
	    			if(task.getTaskId() == taskId) {
	    				iterator.remove();
	    				return;
	    			}
	    		}
	    		throw new Exception("Task with taskId " + taskId + " not found.");
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }

	    public static List<Task> getTasksForUser(User user) {
			return null;
	    }

	    public void markAsComplete() {
	    }

	}


