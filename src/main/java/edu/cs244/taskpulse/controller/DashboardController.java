package edu.cs244.taskpulse.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.cs244.taskpulse.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

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
	private GridPane gridPane;

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

	private List<Task> getData() {

		List<Task> tasks = new ArrayList<>();
		Task task;

		for (int i = 0; i < 20; i++) {
			task = new Task("Hello World");
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tasks.addAll(getData());
		int column = 0;
		int row = 1;
		try {
			for (int i = 0; i < tasks.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/fxml/Task.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();

				TaskController taskController = fxmlLoader.getController();
				taskController.setData(tasks.get(i));

				if (column == 4) {
					column = 0;
					row++;
				}

				//set grid width
				gridPane.add(anchorPane, column++, row);
				gridPane.setAlignment(Pos.CENTER);
				gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
				
				//set grid height
				gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
				
				
				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
