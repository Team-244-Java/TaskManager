package edu.cs244.taskpulse.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.models.Task;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TaskController {

	@FXML
	private Label description;

	@FXML
	private Label dueDate;

	@FXML
	private FontAwesomeIcon editBtn;

	@FXML
	private Label status;
	
    @FXML
    private Label assignTo;

	@FXML
	private ImageView task;

	@FXML
	private Label taskTitle;

	private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

	private Task taskItem;

	public void setData(Task taskItem) {
		this.taskItem = taskItem;
		this.taskTitle.setText(taskItem.getTitle());
		this.description.setText(taskItem.getTitle());
		this.dueDate.setText(taskItem.getDueDate());
		this.status.setText(taskItem.getStatus());
		this.assignTo.setText(getUserName(taskItem.getAssignedTo()));
	}

	@FXML
	void onActionEdit() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskEdit.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage = new Stage();
			
			TaskEditController taskEditController = fxmlLoader.getController();
			
			taskEditController.setTaskItem(taskItem);
			taskEditController.setDashboardController(dashboardController);
			
			stage.setTitle("Task Edit");
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getUserName(int userId) {
	    String username ="";
	    try (Connection connection = DatabaseHandler.getConnection()) {
	        String query = "SELECT username FROM users WHERE id = ?";
	        try (PreparedStatement ps = connection.prepareStatement(query)) {
	            ps.setInt(1, userId);
	            try (ResultSet result = ps.executeQuery()) {
	                if (result.next()) {
	                	username = result.getString("username"); 
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle or log the exception appropriately
	    }
	    return username;
	}
}
