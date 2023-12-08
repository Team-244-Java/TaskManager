package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.models.Task;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskEditController implements Initializable {

	@FXML
	private AnchorPane TaskAssignWindowAnchorPane;

	@FXML
	private ComboBox<String> TaskEditAssignedToComboBox;

	@FXML
	private Button TaskEditDeleteButton;

	@FXML
	private Label TaskEditLabelTwo;

	@FXML
	private ComboBox<String> TaskEditStatusBox;

	@FXML
	private Button TaskEditWindowCancelTaskButton;

	@FXML
	private DatePicker TaskEditWindowDatePicker;

	@FXML
	private HTMLEditor TaskEditWindowHTMLEditor;

	@FXML
	private Text TaskEditWindowLabelOne;

	@FXML
	private Text TaskEditWindowLabelTwo;

	@FXML
	private Button TaskEditWindowSaveTaskButton;

	@FXML
	private TextField TaskEditWindowTaskNameTextField;

	@FXML
	private Text TaskStatusLabel;

	private DashboardController dashboardController;

	public void setDashboardController(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	private Task taskItem;

	private String[] status = { "To Do", "In Progress", "Done" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TaskEditStatusBox.getItems().addAll(status);
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
		TaskEditAssignedToComboBox.getItems().setAll(userNames);
		return TaskEditAssignedToComboBox;
	}

	@FXML
	void CancelTaskAction(ActionEvent event) {
		Stage currentStage = (Stage) TaskEditWindowCancelTaskButton.getScene().getWindow();
		currentStage.close();
	}

	@FXML
	void SaveTaskAction(ActionEvent event) {
		// get task data
		String title = TaskEditWindowTaskNameTextField.getText();
		String description = TaskEditWindowHTMLEditor.getHtmlText();
		String strippedText = description.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "");
		// convert date to string
		LocalDate dueDate = TaskEditWindowDatePicker.getValue();
		String sDate = dueDate.toString();
		String status = TaskEditStatusBox.getValue();
		int taskId = this.taskItem.getTaskId();
		int assignTo = getUserId(TaskEditAssignedToComboBox.getValue());

		Task.updateTask(title, strippedText, sDate, status, taskId, assignTo);
		Stage currentStage = (Stage) TaskEditWindowCancelTaskButton.getScene().getWindow();
		refresh();
		currentStage.close();
		refresh();
	}

	@FXML
	void DeleteTaskAction(ActionEvent event) {
		int taskId = this.taskItem.getTaskId();
		Task.deleteTask(taskId);
		Stage currentStage = (Stage) TaskEditDeleteButton.getScene().getWindow();
		refresh();
		currentStage.close();
		refresh();
	}

	public void setTaskItem(Task taskItem) {
		this.taskItem = taskItem;

		this.TaskEditWindowTaskNameTextField.setText(this.taskItem.getTitle());
		LocalDate dueDate = LocalDate.parse(this.taskItem.getDueDate());
		this.TaskEditWindowDatePicker.setValue(dueDate);
		this.TaskEditWindowHTMLEditor.setHtmlText(this.taskItem.getDescription());
		this.TaskStatusLabel.setText(this.taskItem.getStatus());

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
