package edu.cs244.taskpulse.controller;

import java.util.Optional;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.models.Reminder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReminderController {

	@FXML
	private AnchorPane reminderAnchor;

	@FXML
	private FontAwesomeIcon editBtn;
	
    @FXML
    private FontAwesomeIcon deleteBtn;

	@FXML
	private Label frequency;

	@FXML
	private Label time;

	@FXML
	private Label title;

	private Reminder reminderItem;

	private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
	public void setReminderData(Reminder reminderItem) {
		this.reminderItem = reminderItem;
		this.title.setText(reminderItem.getTitle());
		this.frequency.setText(reminderItem.getFrequency());
		this.time.setText(reminderItem.getDate().toLocalTime().getHour() + ":"
				+ reminderItem.getDate().toLocalTime().getMinute());
	}

	@FXML
	void editReminder() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateReminder.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			CreateReminderController createReminderController = fxmlLoader.getController();
			createReminderController.setReminderId(reminderItem.getId());
			createReminderController.setReminderData(reminderItem);
			createReminderController.setDashboardController(dashboardController);
			stage.setTitle("Reminder Edit");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void deleteReminder() {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Reminder");
        alert.setContentText("Are you sure you want to delete this reminder?");
        
        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(deleteButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == deleteButton) {
            // User clicked Delete, proceed with deletion
            Reminder.removeReminder(reminderItem.getId());
            // Use the reference to the DashboardController to refresh the UI
            
            dashboardController.refreshReminder();
        }
    }

}
