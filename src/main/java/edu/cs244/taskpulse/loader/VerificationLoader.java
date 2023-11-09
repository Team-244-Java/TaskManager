package edu.cs244.taskpulse.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VerificationLoader {
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Verification.fxml"));
			primaryStage.setTitle("Verification");
			primaryStage.setScene(new Scene(root, 400, 400));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
