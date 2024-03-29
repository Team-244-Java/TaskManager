package edu.cs244.taskpulse.loader;

import edu.cs244.taskpulse.controller.DashboardController;
import edu.cs244.taskpulse.controller.ProfileSettingController;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class ProfileSettingsLoader {
	
	private DashboardController dashboardController;

	public ProfileSettingsLoader(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}
	
	public ProfileSettingsLoader() {
	}
	public void newWindow() {
	try {
		FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/fxml/ProfileSetting.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		
		ProfileSettingController profile = (ProfileSettingController)fxmlLoader.getController();
		profile.updatePicture(ProfileSettingController.getPicture());
		profile.updateUsernameLabel();
		profile.setDashboardController(dashboardController);
		Image icon = new Image("/images/PageIcon.png");
		stage.getIcons().add(icon);
		stage.setTitle("Profile Setting");
		stage.setScene(new Scene(root1));
		stage.show();
	} catch (Exception e) {
		e.printStackTrace();
		}
	}
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProfileSetting.fxml"));
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProfileSetting.fxml"));
			ProfileSettingController profile = (ProfileSettingController)fxmlLoader.getController();
			profile.updatePicture(ProfileSettingController.getPicture());
			profile.updateUsernameLabel();
			profile.setDashboardController(dashboardController);
			
			primaryStage.setTitle("Profile Setting");
			primaryStage.setScene(new Scene(root, 1220, 740));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
