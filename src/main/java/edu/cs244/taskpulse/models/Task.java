package edu.cs244.taskpulse.models;

import java.util.List;

public class Task {
	
	    private int taskId;
	    private String title;
	    private String description;
	    private String dueDate;
	    private String status;
	    private int userId;
	    private String priority;

	    //Constructor : setting the defaults...
	    public Task(int taskId, String title, String description, String dueDate, String status, int userId, String priority) {
	        this.taskId = taskId;
	        this.title = title;
	        this.description = description;
	        this.dueDate = dueDate;
	        this.status = status;
	        this.userId = userId;
	        this.priority = priority;
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

	    public void addTask() {
	    }

	    public void updateTask() {
	    }

	    public void deleteTask() {
	    }

	    public static List<Task> getTasksForUser(User user) {
			return null;
	    }

	    public void markAsComplete() {
	    }

	}


