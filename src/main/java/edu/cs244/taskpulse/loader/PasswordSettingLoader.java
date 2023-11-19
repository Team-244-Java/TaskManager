package edu.cs244.taskpulse.loader;

import edu.cs244.taskpulse.controller.PasswordSettingController;
import edu.cs244.taskpulse.controller.ProfileSettingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;



public class PasswordSettingLoader {
	
	public void open(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/PasswordSetting.fxml"));
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
			
			PasswordSettingController profile = (PasswordSettingController)fxmlLoader.getController();
//			profile.updatePicture(ProfileSettingController.getPicture());
			
			stage.setTitle("Profile");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	

}