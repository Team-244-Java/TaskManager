package edu.cs244.taskpulse.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VerificationLoader {
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Verification.fxml"));
			primaryStage.setTitle("Verification");
			Image icon = new Image("/images/PageIcon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(new Scene(root, 1000, 600));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
