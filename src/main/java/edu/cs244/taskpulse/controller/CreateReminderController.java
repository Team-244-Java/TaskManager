package edu.cs244.taskpulse.controller;

import java.time.LocalDate;

import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.models.Reminder;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateReminderController {

	@FXML
	private Button cancelButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextArea noteTextArea;

	@FXML
	private Button saveButton;

	@FXML
	private TextField titleTextField;

	@FXML
	void cancelPopup() {
		Stage currentStage = (Stage) cancelButton.getScene().getWindow();
		currentStage.close();
	}

	@FXML
	void saveReminder() {

		String title = titleTextField.getText();
		String note = noteTextArea.getText();
		LocalDate date = datePicker.getValue();
		int userId = UserSession.getCurrentUser().getUserId();

		Reminder.addReminder(title, note, date, userId);

		Stage currentStage = (Stage) saveButton.getScene().getWindow();
		currentStage.close();
	}

}
