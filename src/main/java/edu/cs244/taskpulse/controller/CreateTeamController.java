package edu.cs244.taskpulse.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import edu.cs244.taskpulse.models.Team;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTeamController implements Initializable {

	@FXML
	private Button cancelButton;

	@FXML
	private CheckComboBox<String> checkComboBox;

	@FXML
	private Button createBtn;

	@FXML
	private TextField titleTextField;

	private DashboardController dashboardController;

	public void setDashboardController(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	@FXML
	void cancelPopup() {
		Stage currentStage = (Stage) cancelButton.getScene().getWindow();
		currentStage.close();
	}

	@FXML
	void createTeam() {
		createNewTeam();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ObservableList<String> userNames = FXCollections.observableArrayList(getUsers());
			userNames.removeIf(userName -> userName.equals(UserSession.getCurrentUser().getUsername()));
			checkComboBox.getItems().setAll(userNames);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<String> getUsers() throws SQLException {

		List<String> users = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					users.add(resultSet.getString("username"));
				}
			}
		}
		return users;
	}

	public void createNewTeam() {
		String team_name = titleTextField.getText();
		int creatorId = UserSession.getCurrentUser().getUserId();

		// Retrieve the selected user IDs from the CheckComboBox
		ObservableList<String> selectedUsernames = checkComboBox.getCheckModel().getCheckedItems();
		List<Integer> selectedUserIds = getUserIdsFromDatabase(selectedUsernames);

		if (!team_name.isEmpty() && !selectedUserIds.isEmpty()) {
			// Attempt to create the team
			boolean teamCreationSuccess = Team.createTeam(team_name, creatorId, selectedUserIds, checkComboBox);

			if (teamCreationSuccess) {
				// Team created successfully, close the Create Team UI
				closeCreateTeamUI();

				// Optionally, you can show a success message to the user
				showSuccessMessage("Team created successfully!");
				refreshDashboard();
			} else {
				// Optionally, you can show a custom error message to the user
				showErrorMessage("Failed to create the team. Please try again.");
			}
		} else {
			// Show an error message to the user if team name or users are not selected
			showErrorMessage("Team name and at least one user must be selected.");
		}
	}

	private List<Integer> getUserIdsFromDatabase(List<String> usernames) {
		List<Integer> userIDs = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection()) {
			String query = "SELECT id FROM users WHERE username = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				for (String username : usernames) {
					preparedStatement.setString(1, username);
					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						if (resultSet.next()) {
							userIDs.add(resultSet.getInt("id"));
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userIDs;
	}

	private void showSuccessMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showErrorMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void closeCreateTeamUI() {
		Stage currentStage = (Stage) createBtn.getScene().getWindow();
		currentStage.close();
	}
	
	private void refreshDashboard() {
        if (dashboardController != null) {
            dashboardController.onNewTeamAdded();
        }
    }

}
