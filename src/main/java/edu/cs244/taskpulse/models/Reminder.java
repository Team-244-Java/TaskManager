package edu.cs244.taskpulse.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

import edu.cs244.taskpulse.utils.DatabaseHandler;

public class Reminder {

	private int reminderid;
	private String title;
	private String note;
	private LocalDateTime date;
	private String frequency;

	public Reminder(int reminderid, String title, String note, LocalDateTime date, String frequency) {
		this.reminderid = reminderid;
		this.title = title;
		this.note = note;
		this.date = date;
		this.frequency = frequency;
	}

	public int getId() {
		return reminderid;
	}

	public void setId(int reminderid) {
		this.reminderid = reminderid;
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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public static void addReminder(String title, String note, LocalDateTime date, String frequency, int user_id) {

		try {
			Connection connection = DatabaseHandler.getConnection();
			String sql = "INSERT INTO reminders (title, note, reminder_date, frequency, user_id) "
					+ "VALUES (?, ?, ?, ?, ?)";

			PreparedStatement pStmt = connection.prepareStatement(sql);

			pStmt.setString(1, title);
			pStmt.setString(2, note);
			pStmt.setObject(3, date);
			pStmt.setObject(4, frequency);
			pStmt.setInt(5, user_id);

			pStmt.executeUpdate();
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void updateReminder(String title, String note, LocalDateTime date, String frequency, int reminderid) {

		try {
			Connection connection = DatabaseHandler.getConnection();
			PreparedStatement pStmt = connection
					.prepareStatement("UPDATE reminders SET title=?, note=?, reminder_date=?, frequency=? WHERE id= ?");

			pStmt.setString(1, title);
			pStmt.setString(2, note);
			pStmt.setObject(3, date);
			pStmt.setObject(4, frequency);
			pStmt.setInt(5, reminderid);

			pStmt.executeUpdate();
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void removeReminder(int reminderId) {

		try {
			Connection connection = DatabaseHandler.getConnection();

			PreparedStatement pStmt = connection.prepareStatement("DELETE FROM reminders WHERE id = ?");

			pStmt.setInt(1, reminderId);

			pStmt.executeUpdate();
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
