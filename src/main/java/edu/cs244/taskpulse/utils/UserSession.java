package edu.cs244.taskpulse.utils;

import edu.cs244.taskpulse.models.User;

public class UserSession {

	private static User currentUser;

	public UserSession() {
	}

	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	public static User getCurrentUser() {
		return currentUser;
	}
}
