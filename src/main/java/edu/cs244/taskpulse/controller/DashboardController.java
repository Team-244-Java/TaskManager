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

import edu.cs244.taskpulse.models.Task;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

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
					Task task = new Task(resultSet.getString("title"), resultSet.getString("due_date"),
							resultSet.getString("status"), resultSet.getString("description"));
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
			    LocalDate taskDueDays = LocalDate.parse(task.getDueDate());  //test date, need to implement accepting input from task
				
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
		    return "/fxml/Task.fxml"; //plain original working post it.
		}
		else if ((result >= 1) && (result <= 4)) {
		    return "/fxml/TaskDarkYellow.fxml";
		}
		else if ((result >= 5) && (result <= 6)) {
		    return "/fxml/TaskSalmon.fxml";
		}
		else if ((result >= 7) && (result <= 14)) {
		    return "/fxml/TaskPink.fxml";
		}
		else {
		    return "/fxml/TaskBlue.fxml"; //placeholder file, low readability.
		}
	}

}
