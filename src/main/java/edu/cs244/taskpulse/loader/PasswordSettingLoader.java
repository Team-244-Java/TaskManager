package edu.cs244.taskpulse.loader;


import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class PasswordSettingLoader {
	
	public void open(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/PasswordSetting.fxml"));
			Image icon = new Image("/images/PageIcon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Password Setting");
			primaryStage.setScene(new Scene(root, 1220, 740));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void newWindow() {
		try {
			FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/fxml/PasswordSetting.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			Image icon = new Image("/images/PageIcon.png");
			stage.getIcons().add(icon);
			stage.setTitle("Profile");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	

}