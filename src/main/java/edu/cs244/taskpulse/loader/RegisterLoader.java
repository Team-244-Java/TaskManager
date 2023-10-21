package edu.cs244.taskpulse.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterLoader {
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
			primaryStage.setTitle("Registration");
			primaryStage.setScene(new Scene(root, 1220, 740));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
