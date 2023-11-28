package edu.cs244.taskpulse.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExportAndImport {

	public static void Insert(String path) {

		int batchSize = 10;

		Connection connection = null;
		try {
			connection = DatabaseHandler.getConnection();

			String sql = "insert into tasks(title, due_date, status, description, user_id) values(?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			BufferedReader lineReader = new BufferedReader(new FileReader(path));

			String line = null;
			int count = 0;

			lineReader.readLine();
			while ((line = lineReader.readLine()) != null) {
				String[] data = line.split(",");

				String title = data[1];
				String due_date = data[2];
				String status = data[3];
				String description = data[4];
				String user_id = data[5];
				int idPlaceHolder = Integer.parseInt(user_id);

				statement.setString(1, title);
				statement.setString(2, due_date);
				statement.setString(3, status);
				statement.setString(4, description);
				statement.setInt(5, idPlaceHolder);

				statement.addBatch();
				if (count % batchSize == 0) {
					statement.executeBatch();
				}
			}

			lineReader.close();
			statement.executeBatch();
			connection.commit();
			connection.close();

			// test in console for passed info
			System.out.println("Info passed to database.");

			// Generate a popup to confirm. Reload page
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public static void Export(String path) {

		Connection connection = null;
		ResultSet results = null;
		int userid = 14;
		try {
			PrintWriter printWriter1 = new PrintWriter(new File(path));
			StringBuilder stringBuilder = new StringBuilder();

			// create connection to db
			connection = DatabaseHandler.getConnection();
			String query = "select * from tasks WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userid);

			results = statement.executeQuery();

			while (results.next()) {
				stringBuilder.append(results.getString("id"));
				stringBuilder.append(",");
				stringBuilder.append(results.getString("title"));
				stringBuilder.append(",");
				stringBuilder.append(results.getString("due_date"));
				stringBuilder.append(",");
				stringBuilder.append(results.getString("status"));
				stringBuilder.append(",");
				stringBuilder.append(results.getString("description"));
				stringBuilder.append(",");
				stringBuilder.append("\r\n");

			}
			printWriter1.write(stringBuilder.toString());
			printWriter1.close();
			// delete this after testing.
			System.out.println("Check the file on the desktop, Nathan!");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/*FileChooser fileChooser = new FileChooser();fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv files","*.csv"));
	Stage stage = (Stage) updateBtn.getScene().getWindow();
	File selectedFile = fileChooser.showOpenDialog(stage);
	String placeholder = (selectedFile.toURI().toString());
	String CsvUrl = (placeholder.replace("file:", ""));

	Insert(CsvUrl);*/
}
