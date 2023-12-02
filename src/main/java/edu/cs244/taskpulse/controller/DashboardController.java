package edu.cs244.taskpulse.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import edu.cs244.taskpulse.loader.CreateReminderLoader;
import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.loader.CreateTeamLoader;
import edu.cs244.taskpulse.loader.PasswordSettingLoader;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;
import edu.cs244.taskpulse.models.Reminder;
import edu.cs244.taskpulse.models.Task;
import edu.cs244.taskpulse.utils.ChatGPTHttpClient;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.ExportAndImport;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

	private List<Task> tasks = new ArrayList<>();

	private List<Reminder> reminders = new ArrayList<>();

	@FXML
	private AnchorPane DashboardAnchorPaneTop;

	@FXML
	private BorderPane DashboardBorderPaneMAIN;

	@FXML
	private HBox DashboardCreateNewTaskButton;
	
	@FXML
	private Button refreshTaskBtn;
	
	@FXML
	private HBox DashboardEditRegistrationButton;

	@FXML
	private FlowPane DashboardFlowPaneLeft;

	@FXML
	private HBox addReminderBtnLeft;

	@FXML
	private HBox addReminderHBox;

	@FXML
	private Accordion assistantAccordion;

	@FXML
	private TitledPane assistantTitlePane;

	@FXML
	private ImageView chatBoxSendBtn;

	@FXML
	private HBox chatBoxTextBar;

	@FXML
	private TextField chatBoxTextField;

	@FXML
	private VBox chatContainer;

	@FXML
	private ScrollPane chatScrollPane;

	@FXML
	private HBox exportTaskBtn;

	@FXML
	private HBox logoutBtn;

	@FXML
	private HBox refreshBtn;

	@FXML
	private VBox reminderContainer;

	@FXML
	private ScrollPane reminderScrollPane;

	@FXML
	private TitledPane reminderTitlePane;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private HBox searchBar;

	@FXML
	private TextField searchBox;

	@FXML
	private Button searchBtn;

	@FXML
	private HBox settingsBtn;

	@FXML
	private ScrollPane teamScrollPane;

	@FXML
	private VBox teamVBox;

	@FXML
	private TilePane tilePane;

	@FXML
	private HBox uploadTaskBtn;
	
	@FXML
	private Label welcomeUserLabel;

	@FXML
	private HBox createTeamBtn;

	@FXML
	private ComboBox<String> teamShowComboBox;
	
    @FXML
    private VBox teamMemberContainer;

	private ChatGPTHttpClient chatGPTClient = new ChatGPTHttpClient();
	private boolean waitingForGptResponse = false;

	@FXML
	void onSearch() {

	}

	private List<Task> getData(int userId) throws SQLException {

		List<Task> tasks = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {
			preparedStatement.setInt(1, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Task task = new Task(resultSet.getInt("id"), resultSet.getString("title"),
							resultSet.getString("due_date"), resultSet.getString("status"),
							resultSet.getString("description"));
					tasks.add(task);
				}
			}
		}

		return tasks;
	}

	private List<Reminder> getReminderData(int userId) throws SQLException {

		List<Reminder> reminders = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reminders WHERE user_id = ?")) {
			preparedStatement.setInt(1, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Reminder reminder = new Reminder(resultSet.getInt("id"), resultSet.getString("title"),
							resultSet.getString("note"), resultSet.getTimestamp("reminder_date").toLocalDateTime(),
							resultSet.getString("frequency"));
					reminders.add(reminder);
				}
			}
		}

		return reminders;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showUsername();
		loadUserTeamDetails(teamShowComboBox, UserSession.getCurrentUser().getUserId());
		String selectedTeamName = teamShowComboBox.getValue();
		loadTeamMember(getTeamMembers(getTeamId(selectedTeamName)));
		loadTask();
		loadReminder();
	}

	@FXML
	void DashboardCreateNewTaskButton() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskCreation.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Task Creation");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void editProfile() {
		ProfileSettingsLoader profileUi = new ProfileSettingsLoader();
		profileUi.newWindow();

	}

	@FXML
	void editPassword() {
		PasswordSettingLoader passwordUi = new PasswordSettingLoader();
		passwordUi.newWindow();
	}
	@FXML
	void onRefreshTask() {
		Stage currentStage = (Stage) refreshTaskBtn.getScene().getWindow();
		DashboardLoader dashboard = new DashboardLoader();
		dashboard.start(currentStage);
	}
	@FXML
	void textFieldPressEnter() {
		String userMessage = chatBoxTextField.getText().trim();

		// Check if the user message is not blank and not waiting for a response
		if (!userMessage.isEmpty() && !waitingForGptResponse) {
			// Set the flag to indicate that we are waiting for the ChatGPT response
			waitingForGptResponse = true;

			// Disable the ability to enter new messages
			chatBoxTextField.setDisable(true);

			// Create an HBox for the user message
			HBox userMessageHBox = new HBox();

			// Create a TextFlow for the user message
			TextFlow userMessageTextFlow = new TextFlow();
			Text userText = new Text(userMessage);
			userText.setFill(Color.WHITE);
			userMessageTextFlow.setStyle("-fx-background-color: #174AE4;" + "-fx-background-radius: 10px;");
			userMessageTextFlow.setPadding(new Insets(5));
			userMessageTextFlow.getChildren().add(userText);

			// Add the TextFlow to the HBox
			userMessageHBox.getChildren().add(userMessageTextFlow);
			userMessageHBox.setAlignment(Pos.CENTER_RIGHT);
			userMessageHBox.setPadding(new Insets(5, 10, 5, 25));

			// Add the HBox to the VBox
			chatContainer.getChildren().add(userMessageHBox);

			chatBoxTextField.clear();

			// Send user message to ChatGPT asynchronously and get the response
			new Thread(() -> {
				String gptResponse = chatGPTClient.chatGPT(userMessage);

				// Update JavaFX UI components on the JavaFX Application Thread
				javafx.application.Platform.runLater(() -> {
					HBox gptResponseHBox = new HBox();
					gptResponseHBox.setAlignment(Pos.CENTER_LEFT);

					// Create a TextFlow for the ChatGPT response
					TextFlow gptResponseTextFlow = new TextFlow();
					Text gptText = new Text("ChatGPT:\n" + gptResponse);
					gptResponseTextFlow.setStyle("-fx-background-color: #FFFFFF;" + "-fx-background-radius: 10px");
					gptResponseTextFlow.setPadding(new Insets(5));
					gptResponseTextFlow.getChildren().add(gptText);

					// Add the TextFlow to the HBox
					gptResponseHBox.getChildren().add(gptResponseTextFlow);
					gptResponseHBox.setPadding(new Insets(5, 25, 5, 10));

					// Add the HBox to the VBox
					chatContainer.getChildren().add(gptResponseHBox);

					// Scroll to the bottom of the VBox
					chatScrollPane.vvalueProperty().bind(chatContainer.heightProperty());

					// Reset the flag
					waitingForGptResponse = false;

					// Enable the ability to enter new messages
					chatBoxTextField.setDisable(false);

					// Set focus back to the text field
					chatBoxTextField.requestFocus();
				});
			}).start();
		}
	}

	@FXML
	void addNewReminder() {
		CreateReminderLoader reminderUi = new CreateReminderLoader(this);
		reminderUi.newWindow();
	}

	private void loadTask() {
		int userId = UserSession.getCurrentUser().getUserId();

		try {
			tasks.addAll(getData(userId));

			for (int i = 0; i < tasks.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				Task task = tasks.get(i);
				LocalDate today = LocalDate.now();
				LocalDate taskDueDays = LocalDate.parse(task.getDueDate()); // test date, need to implement accepting
																			// input from task

				long result = today.until(taskDueDays, ChronoUnit.DAYS);
				String color = ColorOfPostIt(result);

				fxmlLoader.setLocation(getClass().getResource(color));
				AnchorPane anchorPane = fxmlLoader.load();

				TaskController taskController = fxmlLoader.getController();
				taskController.setData(tasks.get(i));

				tilePane.getChildren().add(anchorPane);
				tilePane.setPadding(new Insets(10));

			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static String ColorOfPostIt(long result) {
		if (result <= 0) {
			return "/fxml/Task.fxml"; // plain original working post it.
		} else if ((result >= 1) && (result <= 4)) {
			return "/fxml/TaskDarkYellow.fxml";
		} else if ((result >= 5) && (result <= 6)) {
			return "/fxml/TaskSalmon.fxml";
		} else if ((result >= 7) && (result <= 14)) {
			return "/fxml/TaskPink.fxml";
		} else {
			return "/fxml/TaskBlue.fxml"; // placeholder file, low readability.
		}
	}

	void loadReminder() {
		try {
			int userId = UserSession.getCurrentUser().getUserId();
			reminders.addAll(getReminderData(userId));
			for (int a = 0; a < reminders.size(); a++) {
				FXMLLoader reminderFxmlLoader = new FXMLLoader();
				reminderFxmlLoader.setLocation(getClass().getResource("/fxml/reminder.fxml"));
				AnchorPane reminderPane = reminderFxmlLoader.load();

				HBox reminderHbox = new HBox(reminderPane);
				reminderHbox.setAlignment(Pos.CENTER);
				reminderHbox.setPadding(new Insets(10, 5, 10, 5));

				ReminderController reminderController = reminderFxmlLoader.getController();
				reminderController.setReminderData(reminders.get(a));

				reminderController.setDashboardController(this);

				reminderContainer.getChildren().add(reminderHbox);
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void refreshReminder() {
		reminders.clear();
		reminderContainer.getChildren().clear();
		loadReminder();
	}

	public void onReminderAddedUpdated() {
		reminders.clear();
		reminderContainer.getChildren().clear();
		loadReminder();
	}
	

	public void onNewTeamAdded() {
		teamShowComboBox.getItems().clear();
		loadUserTeamDetails(teamShowComboBox, UserSession.getCurrentUser().getUserId());
	}

	private void showUsername() {
		welcomeUserLabel.setText("Welcome " + UserSession.getCurrentUser().getUsername());
	}

	@FXML
	void exportTask() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
		fileChooser.setInitialFileName("Tasks.csv");
		File savedFile = fileChooser.showSaveDialog(null);
		System.out.println(savedFile);
		
		if (savedFile != null) {
			String path = new String(savedFile.toURI().toString());
			System.out.println(path);
			ExportAndImport.Export(path);
			onRefreshTask();
			
		}
       
	}
	

	@FXML
	void uploadTask() {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Open a file");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(null);
//			show the file dialog
		    	
		if (selectedFile != null) {
			String path = selectedFile.getPath();
		    ExportAndImport.Insert(path);
		    onRefreshTask();
		}
	}
	

	@FXML
	void logout() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout");
        alert.setContentText("Are you sure you want to logouut?");
        
        ButtonType logoutButton = new ButtonType("Logout", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(logoutButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == logoutButton) {
    		Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
    		currentStage.close();

        }
	}

	@FXML
	void createTeam() {
		CreateTeamLoader teamUi = new CreateTeamLoader(this);
		teamUi.newWindow();
	}

	public List<String> getTeamsForUser(int user_id) {
		List<String> teamNames = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection()) {
			String query = "SELECT t.team_name FROM teams t JOIN team_members tm ON t.team_id = tm.team_id WHERE tm.user_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setInt(1, user_id);

				try (ResultSet result = ps.executeQuery()) {
					while (result.next()) {
						teamNames.add(result.getString("team_name"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamNames;
	}

	public void loadUserTeamDetails(ComboBox<String> comboBox, int userId) {

		List<String> teamNames = getTeamsForUser(userId);

		ObservableList<String> observableTeamNames = FXCollections.observableArrayList(teamNames);

		comboBox.setItems(observableTeamNames);

		if (!observableTeamNames.isEmpty()) {
			comboBox.getSelectionModel().selectFirst();
		}else {
	        comboBox.setPromptText("No Team");
	    }
	    comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null) {
	            String selectedTeamName = newValue;
	            int selectedTeamId = getTeamId(selectedTeamName);
	            loadTeamMember(getTeamMembers(selectedTeamId));
	        }
	    });
	}
	
	public int getTeamId(String team_name) {
		int teamId = 0;
		try (Connection connection = DatabaseHandler.getConnection()) {
			String query = "SELECT team_id FROM teams WHERE team_name = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, team_name);
				try (ResultSet result = ps.executeQuery()) {
					while (result.next()) {
						teamId =result.getInt("team_id");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamId;
	}
	
	public List<String> getTeamMembers(int teamId){
		List<String> teamMembers = new ArrayList<>();

		try (Connection connection = DatabaseHandler.getConnection()) {
			String query = "SELECT u.username FROM users u  JOIN team_members tm ON u.id = tm.user_id WHERE tm.team_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setInt(1, teamId);

				try (ResultSet result = ps.executeQuery()) {
					while (result.next()) {
						teamMembers.add(result.getString("username"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamMembers;
	}
	
	public void loadTeamMember(List<String> teamMembers) {
		 teamMemberContainer.getChildren().clear();
		 for (String member : teamMembers) {
	            HBox teamMemberHBox = createTeamMemberHBox(member);
	            teamMemberContainer.getChildren().add(teamMemberHBox);
	        }
	}
	
    private HBox createTeamMemberHBox(String member) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TeamMember.fxml"));
            HBox teamMemberHBox = loader.load();

            TeamMemberController teamMemberController = loader.getController();

            teamMemberController.setData(member);

            return teamMemberHBox;
        } catch (IOException e) {
            e.printStackTrace();
            return new HBox(); // Return an empty HBox in case of an error
        }
    }
}
