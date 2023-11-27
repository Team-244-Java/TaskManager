package edu.cs244.taskpulse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TeamMemberController {
	@FXML
	private ImageView userAvatar;

	@FXML
	private Label usernameLabel;


	public void setData(String username) {
		usernameLabel.setText(username);
	}
}