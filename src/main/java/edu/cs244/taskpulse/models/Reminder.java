package edu.cs244.taskpulse.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

import edu.cs244.taskpulse.utils.DatabaseHandler;

public class Reminder {

	private String title;
	private String note;
	private LocalDateTime date;

	public Reminder(String title, String note, LocalDateTime date) {
		super();
		this.title = title;
		this.note = note;
		this.date = date;
	}

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

	public static void addReminder(String title, String note, LocalDateTime date, int user_id) {
		Connection connection = null;
		try {
			String sql = "INSERT INTO reminders (title, note, reminder_date, user_id) " + "VALUES (?, ?, ?, ?)";
			connection = DatabaseHandler.getConnection();

			PreparedStatement pStmt = connection.prepareStatement(sql);

			pStmt.setString(1, title);
			pStmt.setString(2, note);
			pStmt.setObject(3, date);
			pStmt.setInt(4, user_id);

			pStmt.executeUpdate();
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void removeReminder(int reminderId) {
		Connection connection = null;
		try {
			String sql = "DELETE FROM reminders WHERE id = ?";
			connection = DatabaseHandler.getConnection();

			PreparedStatement pStmt = connection.prepareStatement(sql);

			pStmt.setInt(1, reminderId);

			pStmt.executeUpdate();
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
