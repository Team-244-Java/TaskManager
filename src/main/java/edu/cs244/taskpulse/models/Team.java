package edu.cs244.taskpulse.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import edu.cs244.taskpulse.utils.DatabaseHandler;

public class Team {

	private String teamName;
	private List<User> members;

	public Team(String teamName) {
		this.teamName = teamName;
	}

	public Team(String teamName, List<User> members) {
		this.teamName = teamName;
		this.members = members;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public static boolean createTeam(String teamName, int creatorId, List<Integer> userIds) {
		try (Connection connection = DatabaseHandler.getConnection()) {
			connection.setAutoCommit(false); // Start a transaction

			try {
				// Create New Team
				String createTeamQuery = "INSERT INTO teams (team_name, creator_id) VALUES (?, ?)";

				try (PreparedStatement pStmt = connection.prepareStatement(createTeamQuery,
						Statement.RETURN_GENERATED_KEYS)) {
					pStmt.setString(1, teamName);
					pStmt.setInt(2, creatorId);

					pStmt.executeUpdate();
					try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int teamId = generatedKeys.getInt(1);

							// Insert team members
							String insertTeamMemberQuery = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";
							try (PreparedStatement memberStmt = connection.prepareStatement(insertTeamMemberQuery)) {
								//Insert creator into the team
								memberStmt.setInt(1, teamId);
                                memberStmt.setInt(2, creatorId);
                                memberStmt.executeUpdate();
                                
								for (int userId : userIds) {
									memberStmt.setInt(1, teamId);
									memberStmt.setInt(2, userId);
									memberStmt.executeUpdate();
								}
							}
						} else {
							connection.rollback();
							return false; // Failed to retrieve team ID after insertion
						}
					}
				}

				connection.commit(); // Commit the transaction
				return true; // Team creation successful
			} catch (Exception ex) {
				connection.rollback(); // Roll back the transaction in case of an exception
				return false; // Failed to create the team
			}
		} catch (Exception ex) {
			return false; // Database error
		}
	}

}
