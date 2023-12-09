package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.models.Task;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;

import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TaskCreationController implements Initializable {

	@FXML
	private AnchorPane TaskAssignWindowAnchorPane;

	@FXML
	private ComboBox<String> TaskCreationAssignedToComboBox;

	@FXML
	private Label TaskCreationLabelTwo;

	@FXML
	private ComboBox<String> TaskCreationStatusBox;

	@FXML
	private Button TaskCreationWindowCancelTaskButton;

	@FXML
	private Button TaskCreationWindowCreateTaskButton;

	@FXML
	private DatePicker TaskCreationWindowDatePicker;

	@FXML
	private HTMLEditor TaskCreationWindowHTMLEditor;

	@FXML
	private Text TaskCreationWindowLabelOne;

	@FXML
	private Text TaskCreationWindowLabelTwo;

	@FXML
	private TextField TaskCreationWindowTaskNameTextField;

	@FXML
	private Text TaskStatusLabel;

	private DashboardController dashboardController;

	public void setDashboardController(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	public TaskCreationController() {
		// Default constructor
	}

	private String[] status = { "To Do", "In Progress", "Done" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TaskCreationStatusBox.getItems().addAll(status);
		try {
			loadTeamMembers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ComboBox<String> loadTeamMembers() throws SQLException {
		int teamId = UserSession.getCurrentUser().getCurrentTeamId();
		ObservableList<String> userNames = FXCollections.observableArrayList(getUsers(teamId));
		TaskCreationAssignedToComboBox.getItems().setAll(userNames);
		return TaskCreationAssignedToComboBox;
	}

	@FXML
	void CreateTaskAction(ActionEvent event) {
		String title = TaskCreationWindowTaskNameTextField.getText();
		String description = TaskCreationWindowHTMLEditor.getHtmlText();
		String text = Jsoup.parse(description).text();
		// convert date to string
		LocalDate dueDate = TaskCreationWindowDatePicker.getValue();
		String sDate = dueDate.toString();
		String status = TaskCreationStatusBox.getValue();
		int userId = UserSession.getCurrentUser().getUserId();
		int teams_id = dashboardController.getSelecttedTeamId();
		int assignTo = getUserId(TaskCreationAssignedToComboBox.getValue());
		// send to database
		Task.addTask(title, text, sDate, status, userId, teams_id, assignTo);

		// load changes and close previous page
		Stage currentStage = (Stage) TaskCreationWindowCreateTaskButton.getScene().getWindow();
		refresh();
		currentStage.close();

		refresh();
	}

	@FXML
	void CancelTaskAction(ActionEvent event) {
		Stage currentStage = (Stage) TaskCreationWindowCancelTaskButton.getScene().getWindow();
		currentStage.close();
	}

	private List<String> getUsers(int teamId) throws SQLException {

		List<String> users = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT u.username FROM users u  JOIN team_members tm ON u.id = tm.user_id WHERE tm.team_id = ?")) {
			preparedStatement.setInt(1, teamId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					users.add(resultSet.getString("username"));
				}
			}
		}
		return users;
	}

	private int getUserId(String username) {
		int userId = 0;
		try (Connection connection = DatabaseHandler.getConnection()) {
			String query = "SELECT id FROM users WHERE username = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, username);
				try (ResultSet result = ps.executeQuery()) {
					if (result.next()) {
						userId = result.getInt("id");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle or log the exception appropriately
		}
		return userId;
	}

	private void refresh() {
		if (dashboardController != null) {
			dashboardController.onNewTaskAdded();
		}
	}
}
