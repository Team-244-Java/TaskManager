package edu.cs244.taskpulse.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.loader.CreateReminderLoader;
import edu.cs244.taskpulse.loader.PasswordSettingLoader;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;
import edu.cs244.taskpulse.models.Task;
import edu.cs244.taskpulse.utils.ChatGPTHttpClient;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

	private List<Task> tasks = new ArrayList<>();

	@FXML
	private AnchorPane DashboardAnchorPaneTop;

	@FXML
	private BorderPane DashboardBorderPaneMAIN;

	@FXML
	private Button DashboardCreateNewTaskButton;

	@FXML
	private Button DashboardEditRegistrationButton;

	@FXML
	private Button DashboardEditTasksButton;

	@FXML
	private Button DashboardEmailTaskOwnerButton;

	@FXML
	private Button DashboardFeatureRequestButton;

	@FXML
	private Button DashboardFeatureRequestButton1;

	@FXML
	private FlowPane DashboardFlowPaneLeft;

	@FXML
	private TilePane tilePane;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private HBox searchBar;

	@FXML
	private TextField searchBox;

	@FXML
	private Button searchBtn;

	@FXML
	private ScrollPane chatScrollPane;

	@FXML
	private VBox chatContainer;

	@FXML
	private TextField chatBoxTextField;

	@FXML
	private TitledPane accordion;
	
    @FXML
    private TitledPane assistantTitlePane;
	
    @FXML
    private FontAwesomeIcon chatBoxSendBtn;

    @FXML
    private HBox chatBoxTextBar;
    
    @FXML
    private Accordion assistantAccordion;
    
    @FXML
    private TitledPane reminderAccordion;
    
    @FXML
    private VBox reminderContainer;

    @FXML
    private ScrollPane reminderScrollPane;

    @FXML
    private TitledPane reminderTitlePane;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
			userMessageHBox.setPadding(new Insets(5, 5, 5, 10));

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
					gptResponseHBox.setPadding(new Insets(5, 10, 5, 5));

					// Add the HBox to the VBox
					chatContainer.getChildren().add(gptResponseHBox);

					// Scroll to the bottom of the VBox
					chatScrollPane.setVvalue(1.0);

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
    	CreateReminderLoader reminderUi = new CreateReminderLoader();
    	reminderUi.newWindow();
    }

}
