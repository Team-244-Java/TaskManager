package edu.cs244.taskpulse.gui;

import edu.cs244.taskpulse.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class LoginScreen {

	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		// Username Label
		Label nameLabel = new Label("Username:");
		GridPane.setConstraints(nameLabel, 0, 0);

		// Username Input
		TextField nameInput = new TextField();
		nameInput.setPromptText("username");
		GridPane.setConstraints(nameInput, 1, 0);

		// Password Label
		Label passLabel = new Label("Password:");
		GridPane.setConstraints(passLabel, 0, 1);

		// Password Input
		PasswordField passInput = new PasswordField();
		passInput.setPromptText("password");
		GridPane.setConstraints(passInput, 1, 1);

		// Login Button
		Button loginButton = new Button("Login");
		GridPane.setConstraints(loginButton, 1, 2);
		loginButton.setOnAction(e -> {
			boolean isAuthenticated = authenticate(nameInput.getText(), passInput.getText());
			if (isAuthenticated) {
				// Navigate to another scene or show a welcome message
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Login Error");
				alert.setHeaderText("Authentication Failed");
				alert.setContentText("Invalid username or password!");
				alert.showAndWait();
			}
		});

		grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);

		Scene scene = new Scene(grid, 500, 350);
		scene.getStylesheets().add(getClass().getResource("/loginStyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login to TaskPulse");
		primaryStage.show();
	}

	private boolean authenticate(String username, String password) {
		return User.login(username, password);

	}
}
