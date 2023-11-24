package edu.cs244.taskpulse.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import edu.cs244.taskpulse.models.Reminder;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateReminderController implements Initializable {

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextArea noteTextArea;

	@FXML
	private Button saveButton;

	@FXML
	private TextField titleTextField;

	@FXML
	private Spinner<Integer> hourSpinner;

	@FXML
	private Spinner<Integer> minuteSpinner;

	private Reminder reminderItem;

	private int reminderId = -1;
	
	private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	public void setReminderData(Reminder reminderItem) {
		this.reminderItem = reminderItem;
		this.titleTextField.setText(reminderItem.getTitle());
		this.noteTextArea.setText(reminderItem.getDescription());
		this.comboBox.setValue(reminderItem.getFrequency());
		this.datePicker.setValue(reminderItem.getDate().toLocalDate());
		this.reminderItem.getDate().toLocalTime().getHour();
		SpinnerValueFactory<Integer> hourvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 24,
				reminderItem.getDate().toLocalTime().getHour());
		this.hourSpinner.setValueFactory(hourvalueFactory);
		SpinnerValueFactory<Integer> minutevalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59,
				reminderItem.getDate().toLocalTime().getMinute());
		this.minuteSpinner.setValueFactory(minutevalueFactory);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBox.setItems(FXCollections.observableArrayList("Every Year", "Every Month", "Every Week", "Every Day"));
		// Initialize Spinner values and factories
		initializeSpinner(hourSpinner, 0, 23, 00);
		initializeSpinner(minuteSpinner, 0, 59, 00);
	}

	@FXML
	void cancelPopup() {
		Stage currentStage = (Stage) cancelButton.getScene().getWindow();
		currentStage.close();
	}

	@FXML
	void saveReminder() {

		if (reminderId != -1) {
			updateExistingReminder();
		} else {
			createNewReminder();
		}
		refresh();
		closeStage();
		
	}

	private void initializeSpinner(Spinner<Integer> spinner, int min, int max, int initial) {
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max,
				initial);
		spinner.setValueFactory(valueFactory);
	}

	private void createNewReminder() {
		String title = titleTextField.getText();
		String note = noteTextArea.getText();
		String frequency = comboBox.getValue();
		LocalDate date = datePicker.getValue();
		int hour = hourSpinner.getValue();
		int minute = minuteSpinner.getValue();
		LocalDateTime reminderDate = LocalDateTime.of(date, LocalTime.of(hour, minute));
		int userId = UserSession.getCurrentUser().getUserId();

		Reminder.addReminder(title, note, reminderDate, frequency, userId);
	}

	private void updateExistingReminder() {
		String title = titleTextField.getText();
		String note = noteTextArea.getText();
		String frequency = comboBox.getValue();
		LocalDate date = datePicker.getValue();
		int hour = hourSpinner.getValue();
		int minute = minuteSpinner.getValue();
		LocalDateTime reminderDate = LocalDateTime.of(date, LocalTime.of(hour, minute));
		int reminder_Id = this.reminderItem.getId();

		Reminder.updateReminder(title, note, reminderDate, frequency, reminder_Id);
	}

	private void closeStage() {
		Stage currentStage = (Stage) saveButton.getScene().getWindow();
		currentStage.close();
	}

	private void refresh() {
		if (dashboardController != null) {
            dashboardController.onReminderAddedUpdated();
        }
	}

}
