package edu.cs244.taskpulse.models;

import java.time.LocalDateTime;

public class Reminder {

	private String title;
	private String note;
	private LocalDateTime date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return note;
	}

	public void setDescription(String note) {
		this.note = note;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
