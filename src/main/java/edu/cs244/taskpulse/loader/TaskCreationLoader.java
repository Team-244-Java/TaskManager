package edu.cs244.taskpulse.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskCreationLoader {
	public void newWindow() {
		try {
			FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/fxml/TaskCreation.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			
			stage.setTitle("Task Creation");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
